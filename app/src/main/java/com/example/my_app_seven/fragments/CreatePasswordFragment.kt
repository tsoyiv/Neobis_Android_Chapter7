package com.example.my_app_seven.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.databinding.FragmentCreatePasswordBinding

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toProfilePage()
    }

    private fun toProfilePage() {
        binding.returnProfileRegPageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createPasswordFragment_to_profileRegFragment)
        }
    }
}