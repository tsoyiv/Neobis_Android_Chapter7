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
import com.example.my_app_seven.databinding.FragmentProfileRegBinding

class ProfileRegFragment : Fragment() {

    private lateinit var binding: FragmentProfileRegBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInput()
        toCreatePassword()
    }

    private fun toCreatePassword() {
        binding.regProfileBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_profileRegFragment_to_createPasswordFragment)
        }
    }

    private fun checkInput() {
        binding.inputRegNameProfile.addTextChangedListener(inputTextWatcher)
        binding.inputRegSurnameProfile.addTextChangedListener(inputTextWatcher)
        binding.inputRegBirthProfile.addTextChangedListener(inputTextWatcher)
        binding.inputEmailProfile.addTextChangedListener(inputTextWatcher)
    }
    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val usernameInputName = binding.inputRegNameProfile.text.toString().trim()
            val usernameInputSurname = binding.inputRegSurnameProfile.text.toString().trim()
            val usernameInputDate = binding.inputRegBirthProfile.text.toString().trim()
            val usernameInputEmail = binding.inputEmailProfile.text.toString().trim()

            val button = binding.regProfileBtnNext

            if (!usernameInputEmail.contains("@") || usernameInputDate.isEmpty()
                || usernameInputSurname.isEmpty() || usernameInputName.isEmpty()) {
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