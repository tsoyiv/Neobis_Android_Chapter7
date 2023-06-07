package com.example.my_app_seven.api

import com.example.my_app_seven.models.*
import retrofit2.http.Body
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

interface UserAPI {
//    @POST("/forgot_password/")
//    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<UserAPI>
//
//    @POST("/forgot_password_confirm/")
//    fun forgotPasswordConfirm(@Body request: ForgotPasswordRequest): Call<UserAPI>

    @POST("forgot_password_confirm/")
    fun resetPasswordConfirm(@Body request: ForgotPasswordConfirmRequest): Call<Unit>

    @POST("forgot_password/")
    fun forgotPasswordRequest(@Body request: ForgotPasswordRequest): Call<Unit>

    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register/")
    fun register(@Body request: EmailRegRequest): Call<Unit>

    @POST("register_confirm/")
    fun registerUser(@Body request: UserRegRequest): Call<Unit>
}