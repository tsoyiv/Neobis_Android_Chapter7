package com.example.my_app_seven.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.databinding.FragmentEmailRegBinding

class EmailRegFragment : Fragment() {

    private lateinit var binding: FragmentEmailRegBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmailRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toMainPage()
        toProfilePage()
        checkInput()
    }

    private fun toProfilePage() {
        binding.regEmailBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_emailRegFragment_to_profileRegFragment)
        }
    }

    private fun toMainPage() {
        binding.returnMainPageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_emailRegFragment_to_startFragment)
        }
    }
    private fun checkInput() {
        binding.inputRegEmail.addTextChangedListener(inputTextWatcher)
    }
    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val usernameInput = binding.inputRegEmail.text.toString().trim()

            val button = binding.regEmailBtnNext

            if (!usernameInput.contains("@")) {
                button.isEnabled = false
                button.setBackgroundResource(R.drawable.button_grey)
            } else {
                button.isEnabled = true
                button.setBackgroundResource(R.drawable.rounded_btn)
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
}