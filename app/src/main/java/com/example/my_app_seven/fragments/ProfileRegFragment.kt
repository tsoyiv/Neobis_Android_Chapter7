package com.example.my_app_seven.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_seven.R
import com.example.my_app_seven.api.UserAPI
import com.example.my_app_seven.databinding.FragmentProfileRegBinding
import com.example.my_app_seven.models.UserRegRequest
import com.example.my_app_seven.view_model.RegistrationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ProfileRegFragment : Fragment() {

    private lateinit var binding: FragmentProfileRegBinding
    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickDate()
        checkInput()
        toEmailPageBack()
        fillProfInfo()
    }

    private fun fillProfInfo() {
        binding.regProfileBtnNext.setOnClickListener {
            registrationViewModel.name = binding.inputRegNameProfile.text.toString()
            registrationViewModel.email = binding.inputEmailProfile.text.toString()
            registrationViewModel.birthday = binding.inputRegBirthProfile.text.toString()
            registrationViewModel.lastName = binding.inputRegSurnameProfile.text.toString()

            findNavController().navigate(R.id.action_profileRegFragment_to_createPasswordFragment)
        }
    }

    private fun pickDate() {
        val datePicker = binding.inputRegBirthProfile
        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
                    datePicker.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun toEmailPageBack() {
        binding.returnEmailProfilePageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileRegFragment_to_emailRegFragment)
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
                || usernameInputSurname.isEmpty() || usernameInputName.isEmpty()
            ) {
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