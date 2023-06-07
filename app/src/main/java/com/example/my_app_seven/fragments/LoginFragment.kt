package com.example.my_app_seven.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.api.RetrofitInstance
import com.example.my_app_seven.api.UserAPI
import com.example.my_app_seven.databinding.FragmentLoginBinding
import com.example.my_app_seven.models.LoginRequest
import com.example.my_app_seven.models.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        login()
    }
    private fun login() {
        binding.loginButton.setOnClickListener {
            val email = binding.inputLoginEmail.text.toString()
            val password = binding.inputLoginPassword.text.toString()

            val loginRequest = LoginRequest(email, password)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.api.login(loginRequest)
                    onResponse(response)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private suspend fun onResponse(loginResponse: LoginResponse?) {
        withContext(Dispatchers.Main) {
            if (loginResponse != null) {
                Toast.makeText(context, "user exist", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }
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

            val button = binding.loginButton

            if (passwordInput.isEmpty() || !usernameInput.contains("@")) {
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