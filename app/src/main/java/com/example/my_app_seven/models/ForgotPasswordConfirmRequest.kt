package com.example.my_app_seven.models

data class ForgotPasswordConfirmRequest(
    val new_password: String,
    val new_password_confirm: String,
    val activation_code: String
)
