package com.example.my_app_seven.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.my_app_seven.R
import com.example.my_app_seven.api.RetrofitInstance
import com.example.my_app_seven.api.UserAPI
import com.example.my_app_seven.databinding.FragmentSecondResetPasswordBinding
import com.example.my_app_seven.models.ForgotPasswordConfirmRequest
import com.example.my_app_seven.models.ForgotPasswordRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second_reset_password.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSecondResetPasswordBinding
    private val userAPI: UserAPI = RetrofitInstance.api
    private var isPasswordsSimilar = false
    private var hasUppercase = false
    private var hasNumber = false
    private var hasSymbol = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInput()
        resetPasswordCreate()
    }

    private fun resetPasswordCreate() {
        binding.regResetPasswordBtnSave.setOnClickListener {
            val new_password = binding.inputNewPassword.text.toString()
            val new_password_confirm = binding.inputNewPasswordRepeat.text.toString()
            val activation_code = binding.inputCode.text.toString()

            val resetPasswordRequest = ForgotPasswordConfirmRequest(new_password, new_password_confirm, activation_code)

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = resetPasswordConfirmAsync(resetPasswordRequest)
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        if (errorMessage != null && errorMessage.contains("send code is not correct")) {
                            Toast.makeText(context, "The activation code is incorrect", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun resetPasswordConfirmAsync(request: ForgotPasswordConfirmRequest): Response<Unit> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.resetPasswordConfirm(request)
        }
    }


    private fun checkInput() {
        binding.inputNewPassword.addTextChangedListener(inputTextWatcher)
        binding.inputNewPasswordRepeat.addTextChangedListener(inputTextWatcher)
        binding.inputCode.addTextChangedListener(inputTextWatcher)
    }

    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val button = binding.regResetPasswordBtnSave
            val passwordResetCode = binding.inputCode.text.toString().trim()
            registrationDataCheck()

            if (isPasswordsSimilar && hasUppercase && hasNumber && hasSymbol && passwordResetCode.isNotEmpty()) {
                button.isEnabled = true
                callSnackBar()
                button.setBackgroundResource(R.drawable.rounded_btn)
            } else {
                button.isEnabled = false
                button.setBackgroundResource(R.drawable.button_grey)
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    @SuppressLint("RestrictedApi")
    private fun callSnackBar() {
        binding.regResetPasswordBtnSave.setOnClickListener {
            val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
            val inflater = LayoutInflater.from(snackbar.context)
            val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar, null)

            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.setPadding(0, 0, 0, 0)
            snackbarLayout.addView(customSnackbarLayout, 0)

            val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackbarLayout.layoutParams = params

            snackbar.show()
        }
    }

    private fun registrationDataCheck() {
        val password = binding.inputNewPassword.text.toString()
        val passwordRep = binding.inputNewPasswordRepeat.text.toString()

        binding.apply {
            if (password.matches(Regex(".*[\$&+,:;=?@#|'<>.^*()%!-].*"))) {
                hasSymbol = true
                specialSymbolsImageReset.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                specialSymbolsTextReset.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasSymbol = false
                specialSymbolsImageReset.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                specialSymbolsTextReset.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.equals(passwordRep) && !password.toString()
                    .isEmpty() && !passwordRep.isEmpty()
            ) {
                isPasswordsSimilar = true
                similarPasswordImageReset.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                similarPasswordTextReset.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                isPasswordsSimilar = false
                similarPasswordImageReset.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                similarPasswordTextReset.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.matches(Regex(".*[A-Z].*"))) {
                hasUppercase = true
                upperCaseImageReset.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                upperCaseTextReset.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasUppercase = false
                upperCaseImageReset.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                upperCaseTextReset.setTextColor(Color.parseColor("#C1C1C1"))
            }
            if (password.matches(Regex(".*\\d.*"))) {
                hasNumber = true
                numberImageReset.setCardBackgroundColor(Color.parseColor("#5D5FEF"))
                numberTextReset.setTextColor(Color.parseColor("#5D5FEF"))
            } else {
                hasNumber = false
                numberImageReset.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                numberTextReset.setTextColor(Color.parseColor("#C1C1C1"))
            }
        }
    }
}