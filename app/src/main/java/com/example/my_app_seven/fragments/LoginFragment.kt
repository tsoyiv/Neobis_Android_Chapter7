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
import com.example.my_app_seven.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toResetPassword()
        checkInput()

    }
    private fun toResetPassword() {
        binding.textForgetPsw.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
    }
    private fun checkInput() {
        binding.inputLoginEmail.addTextChangedListener(inputTextWatcher)
        binding.inputLoginPassword.addTextChangedListener(inputTextWatcher)
    }
    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val usernameInput = binding.inputLoginEmail.text.toString().trim()
            val passwordInput = binding.inputLoginPassword.text.toString().trim()

            binding.loginButton.isEnabled = !usernameInput.isEmpty() && !passwordInput.isEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
}