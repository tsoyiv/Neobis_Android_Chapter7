package com.example.my_app_seven.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        returnToMainPage()
    }

    private fun returnToMainPage() {
        binding.returnLoginPageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_startFragment)
        }
    }

    private fun login() {
        binding.loginButton.setOnClickListener {
            val email = binding.inputLoginEmail.text.toString()
            val password = binding.inputLoginPassword.text.toString()

            val loginRequest = LoginRequest(email, password)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        resetErrors()
                    }
                    val response = RetrofitInstance.api.login(loginRequest)
                    onResponse(response)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        checkOnError()
                    }
                }
            }
        }
    }

    private suspend fun onResponse(loginResponse: LoginResponse?) {
        withContext(Dispatchers.Main) {
            if (loginResponse != null) {
                Toast.makeText(context, "You are IN", Toast.LENGTH_SHORT).show()
            } else {
                checkOnError()
            }
        }
    }
    private fun resetErrors() {
        val defaultBoxStrokeColor = ContextCompat.getColor(requireContext(), R.color.defaultBoxStrokeColor)
        val defaultHintTextColor = ContextCompat.getColor(requireContext(), R.color.defaultHintTextColor)

        binding.inputLoginEmailLayout.setBoxStrokeColor(defaultBoxStrokeColor)
        binding.inputLoginEmailLayout.setDefaultHintTextColor(ColorStateList.valueOf(defaultHintTextColor))
        binding.inputLoginPasswordLayout.setBoxStrokeColor(defaultBoxStrokeColor)
        binding.inputLoginPasswordLayout.setDefaultHintTextColor(ColorStateList.valueOf(defaultHintTextColor))
        binding.errorText.setText("")
        binding.errorText.setVisibility(View.GONE)
    }
    private fun checkOnError() {
        binding.inputLoginEmailLayout.setBoxStrokeColor(Color.RED)
        binding.inputLoginEmailLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED))
        binding.inputLoginPasswordLayout.setBoxStrokeColor(Color.RED)
        binding.inputLoginPasswordLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED))
        binding.errorText.setText("Неверный логин или пароль")
        binding.errorText.setVisibility(View.VISIBLE)
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