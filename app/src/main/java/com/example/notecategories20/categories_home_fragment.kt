package com.example.notecategories20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notecategories20.databinding.FragmentCategoriesHomeFragmentBinding
import com.example.notecategories20.databinding.FragmentHomeBinding
import com.google.firebase.database.DatabaseReference


class categories_home_fragment : Fragment() {
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentHomeBinding.inflate(inflater, container,false)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_home_fragment, container, false)
    }
}