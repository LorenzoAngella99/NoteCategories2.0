package com.example.notecategories20

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.notecategories20.Note.Note
import com.example.notecategories20.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddFragment : Fragment() {

    private lateinit var firebaseRef: DatabaseReference

    private val TAG = "addFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseRef = FirebaseDatabase.getInstance().getReference("notes")
        val view = inflater.inflate(R.layout.fragment_add,container,false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firebaseRef = FirebaseDatabase.getInstance().getReference("notes")
        saveData()
    }


    private fun saveData(){
        val textTitle = view?.findViewById<EditText>(R.id.editTextTitle)
        val textContent = view?.findViewById<EditText>(R.id.editTextTextMultiLine)

        val title = textTitle?.text.toString()
        val content = textContent?.text.toString()

        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        Log.d(TAG, "current data $date")

        if(title.isNotEmpty() && content.isNotEmpty()){
            val noteId = firebaseRef.push().key!!
            val _note = Note(noteId,title,date,content)

            firebaseRef.child(noteId).setValue(_note)
                .addOnCompleteListener{
                    //Toast.makeText(requireContext(),"Note Added", Toast.LENGTH_SHORT).show()
                    Log.d(TAG,"Note Added")
                }.addOnFailureListener{
                    //Toast.makeText(requireContext(),"Error ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG,"Error ${it.message}")
                }
        }
    }
}