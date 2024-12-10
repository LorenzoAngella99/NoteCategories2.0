package com.example.notecategories20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notecategories20.databinding.FragmentCategoriesHomeFragmentBinding
import com.google.firebase.database.DatabaseReference


class categories_home_fragment : Fragment() {

    private var _binding: FragmentCategoriesHomeFragmentBinding? = null
    private val binding get() = _binding!!


    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoriesHomeFragmentBinding.inflate(inflater, container,false)
        binding.floatingActionButton2.setOnClickListener {
            TODO("mettere il nav controller del fragment per creare una nuova categoria")
        }



        // Inflate the layout for this fragment
        return binding.root
    }
}