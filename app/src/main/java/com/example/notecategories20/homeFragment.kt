package com.example.notecategories20

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.Note
import com.example.notecategories20.adapter.RvContactsAdapter
import com.example.notecategories20.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter : RvContactsAdapter
    private lateinit var noteList : ArrayList<Note>
    private lateinit var firebaseRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("notes")
        noteList = arrayListOf()
        fetchData()

        binding.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        setSwipeToDelete()
        return binding.root
    }
    private fun fetchData() {
        firebaseRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                if(snapshot.exists()){
                    for(noteSnap in snapshot.children){
                        val noteData = noteSnap.getValue(Note::class.java)
                        noteList.add(noteData!!)
                    }
                }
                rvAdapter = RvContactsAdapter(noteList)
                binding.recyclerView.adapter = rvAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context," ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
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

            // delete note swiped suc
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val modelClass = noteList[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                noteList.removeAt(viewHolder.adapterPosition)

                rvAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)

                firebaseRef.child(modelClass.id.toString()).removeValue() // Rimozione
                    .addOnSuccessListener {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    }

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
        }).attachToRecyclerView(binding.recyclerView)
    }

    // set the icon for swipe to delete ( )
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
            checkNotNull(ContextCompat.getDrawable(this.requireContext(), R.drawable.baseline_delete_24))
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