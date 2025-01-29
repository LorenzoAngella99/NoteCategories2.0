package com.example.notecategories20

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notecategories20.Note.Note
import com.example.notecategories20.databinding.FragmentUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            bar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delateNote->{
                        delateNoteWindowPopup()
                    }
                    R.id.changeCategory->{
                         // mettere il bottom popup che appare di sotto

                    }
                }
                true
            }

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
    private fun delateNoteWindowPopup(){
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        // on below line we are displaying a toast message.
                        findNavController().navigate(R.id.homeFragment)

                        GlobalScope.launch(Dispatchers.Main) { //cancellazione della nota tramite coroutine
                            firebaseRef.child(args.idNote).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Deleted Note ${args.title}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    // on below line we are setting click listener
                    // for our negative button.
                    DialogInterface.BUTTON_NEGATIVE -> {
                        // on below line we are dismissing our dialog box.
                        dialog.dismiss()
                    }
                }
            }
        val builderWindow: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builderWindow.setMessage("Do you wanna delete this note?")
        builderWindow.setTitle("Delete note")
            .setPositiveButton("Yes",dialogClickListener)
            .setNegativeButton("No",dialogClickListener)
            .show()
    }
}

