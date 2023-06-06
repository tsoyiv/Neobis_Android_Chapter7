package com.example.my_app_seven.models

data class ConfirmForgotPasswordRequest(
    val email: String,
    val newPassword: String,
    val passwordConfirm: String,
    val code: String
)
