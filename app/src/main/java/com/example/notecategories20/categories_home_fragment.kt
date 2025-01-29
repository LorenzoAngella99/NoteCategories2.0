package com.example.notecategories20

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.CategoryClass
import com.example.notecategories20.adapter.RvCategoryAdaper
import com.example.notecategories20.databinding.FragmentCategoriesHomeFragmentBinding
import com.example.notecategories20.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.checkerframework.common.subtyping.qual.Bottom


class categories_home_fragment : Fragment() {


    private val TAG = "CategoriesHomeFragment"
    private var _binding: FragmentCategoriesHomeFragmentBinding? = null

    //private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter : RvCategoryAdaper
    private lateinit var categoryList : ArrayList<CategoryClass>
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesHomeFragmentBinding.inflate(inflater, container, false)
        binding.floatingActionButton2.setOnClickListener { //bottom sheet dialog
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.fragemnt_category_bottomsheet, null)

            val btnCreateCategory = view.findViewById<Button>(R.id.BtnCreateCategory)
            val editTextCategory = view.findViewById<EditText>(R.id.BSD_editText)
            btnCreateCategory.setOnClickListener { // save data category

                val idCategory = firebaseRef.push().key!!
                val new_category = CategoryClass(idCategory,editTextCategory.text.toString())

                firebaseRef = FirebaseDatabase.getInstance().getReference("category")
                firebaseRef.child(idCategory).setValue(new_category)

                Toast.makeText(requireContext(), "create category", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
        binding.recyclerViewCa.setOnClickListener {
            findNavController().navigate(R.id.action_categories_home_fragment3_to_updateCategoryFragment)
        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("category")
        categoryList = arrayListOf()
        fetchData()

        binding.recyclerViewCa.apply {
            setHasFixedSize(true) //serve per ottimizzare la performance
            layoutManager = LinearLayoutManager(this.context)
        }
        setSwipeToDelete()
        return binding.root
    }

    private fun fetchData(){
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryList.clear()
                if(snapshot.exists()){
                    for(categorySnap in snapshot.children){
                        val categoryData = categorySnap.getValue(CategoryClass::class.java)
                        categoryList.add(categoryData!!)
                    }
                }
                rvAdapter = RvCategoryAdaper(categoryList)
                binding.recyclerViewCa.adapter = rvAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context," ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun saveDataCategory(){
        firebaseRef = FirebaseDatabase.getInstance().getReference("category")
        val textCategory = view?.findViewById<EditText>(R.id.editTextTitle)
        val categoryName = textCategory?.text.toString()

        val idCategory = firebaseRef.push().key!!
        val new_category = CategoryClass(idCategory,categoryName)

        if(categoryName.isNotEmpty()){
            firebaseRef.child(idCategory).setValue(new_category)
                .addOnCompleteListener {
                    Log.d(TAG,"Category Added")
                }.addOnFailureListener{
                    Log.d(TAG,"Error ${it.message}")
                }
        }else{
            Toast.makeText(requireContext(),"Category name is empty", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val modelClass = categoryList[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                categoryList.removeAt(viewHolder.adapterPosition)

                rvAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)

                firebaseRef.child(modelClass.idCategory.toString()).removeValue() // Rimozione categoria

            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 1f
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                setDeleteIcon(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(binding.recyclerViewCa)
    }

    private fun setDeleteIcon(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val mClearPaint = Paint()
        mClearPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        val mBackground = ColorDrawable()
        val backgroundColor = Color.parseColor("#b80f0a")
        val deleteDrawable =
            checkNotNull(ContextCompat.getDrawable( this.requireContext(), R.drawable.baseline_delete_24))
        val intrinsicWidth = deleteDrawable.intrinsicWidth
        val intrinsicHeight = deleteDrawable.intrinsicHeight

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX == 0f && !isCurrentlyActive

        if (isCancelled) {
            c.drawRect(
                itemView.right + dX, itemView.top.toFloat(),
                itemView.right.toFloat(), itemView.bottom.toFloat(), mClearPaint
            )
            return
        }

        mBackground.color = backgroundColor
        mBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        mBackground.draw(c)

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight


        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable.draw(c)
    }




}