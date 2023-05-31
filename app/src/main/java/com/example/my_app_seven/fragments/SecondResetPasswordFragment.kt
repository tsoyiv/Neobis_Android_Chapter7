package com.example.my_app_seven.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_app_seven.R
import com.example.my_app_seven.databinding.FragmentSecondResetPasswordBinding
import kotlinx.android.synthetic.main.fragment_create_password.*


class SecondResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSecondResetPasswordBinding
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
    }
    private fun checkInput() {
        binding.inputNewPassword.addTextChangedListener(inputTextWatcher)
        binding.inputNewPasswordRepeat.addTextChangedListener(inputTextWatcher)
    }
    private val inputTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val button = binding.regResetPasswordBtnSave
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
            if (password.toString().equals(passwordRep) && !password.toString().isEmpty() && !passwordRep.isEmpty()) {
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