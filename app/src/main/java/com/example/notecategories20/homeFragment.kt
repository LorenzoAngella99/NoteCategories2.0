package com.example.notecategories20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notecategories20.Note.Note
import com.example.notecategories20.adapter.RvContactsAdapter
import com.example.notecategories20.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
                val rvAdapter = RvContactsAdapter(noteList)
                binding.recyclerView.adapter = rvAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context," ERROR: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




}