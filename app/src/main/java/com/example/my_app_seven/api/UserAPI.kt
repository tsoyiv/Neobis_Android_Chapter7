package com.example.my_app_seven.api

import com.example.my_app_seven.models.ForgotPasswordRequest
import com.example.my_app_seven.models.LoginRequest
import com.example.my_app_seven.models.LoginResponse
import com.example.my_app_seven.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.Call
import retrofit2.http.POST

interface UserAPI {
//    @POST("/forgot_password/")
//    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<UserAPI>
//
//    @POST("/forgot_password_confirm/")
//    fun forgotPasswordConfirm(@Body request: ForgotPasswordRequest): Call<UserAPI>

    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

//    @POST("/refresh/")
//    fun refresh(): Call<UserAPI>
//
//    @POST("/register/")
//    fun register(@Body request: RegisterRequest): Call<UserAPI>

//    @POST("/register")
//    fun registerUser(@Body loginRequest: User): Observable<String>
//
//    @POST("/login")
//    fun loginUser(@Body signUpRequest: User): Observable<String>
}