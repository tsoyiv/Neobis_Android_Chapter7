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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.api.RetrofitInstance
import com.example.my_app_seven.api.UserAPI
import com.example.my_app_seven.databinding.FragmentSecondResetPasswordBinding
import com.example.my_app_seven.models.ForgotPasswordConfirmRequest
import com.example.my_app_seven.models.ForgotPasswordRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_snackbar.view.*
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


    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar, null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = FrameLayout(requireContext())
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        snackbarLayout.layoutParams = layoutParams

        snackbarLayout.addView(customSnackbarLayout)
        (snackbarView as Snackbar.SnackbarLayout).addView(snackbarLayout, 0)
        snackbar.show()

        view?.postDelayed({
            findNavController().navigate(R.id.action_secondResetPasswordFragment_to_loginFragment)
        }, 2000)
    }


    private fun resetPasswordCreate() {
        binding.regResetPasswordBtnSave.setOnClickListener {
            val newPassword = binding.inputNewPassword.text.toString()
            val newPasswordConfirm = binding.inputNewPasswordRepeat.text.toString()
            val activationCode = binding.inputCode.text.toString()

            val request =
                ForgotPasswordConfirmRequest(
                    newPassword,
                    newPasswordConfirm,
                    activationCode
                )
            userAPI.resetPasswordConfirm(request)
                .enqueue(object : retrofit2.Callback<Unit> {
                    override fun onResponse(
                        call: Call<Unit>,
                        response: Response<Unit>
                    ) {
                        if (response.isSuccessful) {
                            callSnackBarAndNavigate()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error happened",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                    }
                })
        }
    }


    private fun checkInput() {
        binding.inputNewPassword.addTextChangedListener(inputTextWatcher)
        binding.inputNewPasswordRepeat.addTextChangedListener(inputTextWatcher)
        binding.inputCode.addTextChangedListener(inputTextWatcher)
    }

    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val button = binding.regResetPasswordBtnSave
            val passwordResetCode = binding.inputCode.text.toString().trim()
            registrationDataCheck()

            if (isPasswordsSimilar && hasUppercase && hasNumber && hasSymbol && passwordResetCode.isNotEmpty()) {
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