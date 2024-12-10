package com.example.notecategories20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.notecategories20.Note.Note
import com.example.notecategories20.databinding.FragmentUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("notes")
        binding.apply{
            editTextTitle.setText(args.title)
            editTextTextMultiLine.setText(args.content)
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        firebaseRef = FirebaseDatabase.getInstance().getReference("notes")
        updateData()
    }
    private fun updateData() {
        val title = binding.editTextTitle.text.toString()
        val content = binding.editTextTextMultiLine.text.toString()
        if(title.isNotEmpty() && content.isNotEmpty()){
            val _note = Note(args.idNote,title,args.date,content)
            firebaseRef.child(args.idNote).setValue(_note)
                .addOnSuccessListener {
                    binding.editTextTitle.text?.clear()
                    binding.editTextTextMultiLine.text?.clear()
                }
                .addOnFailureListener {
                    println("errore di aggiornamento")
                }
        }
    }
}

