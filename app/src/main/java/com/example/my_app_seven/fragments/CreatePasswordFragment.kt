package com.example.my_app_seven.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.api.RetrofitInstance
import com.example.my_app_seven.databinding.FragmentCreatePasswordBinding
import com.example.my_app_seven.models.UserRegRequest
import com.example.my_app_seven.view_model.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_create_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    private val registrationViewModel: RegistrationViewModel by activityViewModels()
    private var isPasswordsSimilar = false
    private var hasUppercase = false
    private var hasNumber = false
    private var hasSymbol = false

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
        checkInput()
        creationPassword()
    }

    private fun creationPassword() {
        binding.regCreatePasswordBtnNext.setOnClickListener {
            val password1 = binding.inputCreatePassword.text.toString()
            val password2 = binding.inputCreatePasswordRepeat.text.toString()

            if (password1 == password2) {
                registrationViewModel.password = password1
                registerUser()
            } else {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerUser() {
        val api = RetrofitInstance.api
        val requestBody = UserRegRequest(
            email = registrationViewModel.email ?: "",
            name = registrationViewModel.name ?: "",
            last_name = registrationViewModel.lastName ?: "",
            birthday = registrationViewModel.birthday ?: "",
            password = registrationViewModel.password ?: "",
            password2 = registrationViewModel.password ?: ""
        )

        val call = api.registerUser(requestBody)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "registered", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
            }
        })
    }

    private fun checkInput() {
        binding.inputCreatePassword.addTextChangedListener(inputTextWatcher)
        binding.inputCreatePasswordRepeat.addTextChangedListener(inputTextWatcher)
    }
    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val button = binding.regCreatePasswordBtnNext
            registrationDataCheck()

            if (isPasswordsSimilar && hasUppercase && hasNumber && hasSymbol) {
                button.isEnabled = true
                button.setBackgroundResource(R.drawable.rounded_btn)
            } else {
                button.isEnabled = false
                button.setBackgroundResource(R.drawable.button_grey)
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
    private fun registrationDataCheck() {
        val password = binding.inputCreatePassword.text.toString()
        val passwordRep = binding.inputCreatePasswordRepeat.text.toString()

        binding.apply {
            if (password.matches(Regex(".*[\$&+,:;=?@#|'<>.^*()%!-].*"))) {
                hasSymbol = true
                special_symbols_image.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                special_symbols_text.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasSymbol = false
                special_symbols_image.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                special_symbols_text.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.toString().equals(passwordRep) && !password.toString().isEmpty() && !passwordRep.isEmpty()) {
                isPasswordsSimilar = true
                similar_password_image.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                similar_password_text.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                isPasswordsSimilar = false
                similar_password_image.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                similar_password_text.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.matches(Regex(".*[A-Z].*"))) {
                hasUppercase = true
                upper_case_image.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                upper_case_text.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasUppercase = false
                upper_case_image.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                upper_case_text.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.matches(Regex(".*\\d.*"))) {
                hasNumber = true
                number_image.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                number_text.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasNumber = false
                number_image.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                number_text.setTextColor(Color.parseColor("#C1C1C1"))
            }
        }
    }
    private fun toProfilePage() {
        binding.returnProfileRegPageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createPasswordFragment_to_profileRegFragment)
        }
    }
}