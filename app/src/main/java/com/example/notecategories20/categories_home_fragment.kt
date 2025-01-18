package com.example.notecategories20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notecategories20.Note.CategoryClass
import com.example.notecategories20.adapter.RvCategoryAdaper
import com.example.notecategories20.databinding.FragmentCategoriesHomeFragmentBinding
import com.example.notecategories20.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private lateinit var categoryList : ArrayList<CategoryClass>
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        firebaseRef = FirebaseDatabase.getInstance().getReference("category")
        categoryList = arrayListOf()
        fetchData()

        binding.recyclerViewCa.apply {
            setHasFixedSize(true) //serve per ottimizzare la performance
            layoutManager = LinearLayoutManager(this.context)
        }
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
                val rvAdapter = RvCategoryAdaper(categoryList)
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
}