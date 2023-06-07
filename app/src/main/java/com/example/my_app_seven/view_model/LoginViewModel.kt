package com.example.my_app_seven.view_model

import androidx.lifecycle.ViewModel
import com.example.my_app_seven.api.RetrofitInstance
import com.example.my_app_seven.models.LoginRequest
import com.example.my_app_seven.models.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun login(email: String, password: String, onResponse: (LoginResponse?) -> Unit) {
        val loginRequest = LoginRequest(email, password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.login(loginRequest)
                onResponse(response)
            } catch (e: Exception) {
                onResponse(null)
            }
        }
    }
}