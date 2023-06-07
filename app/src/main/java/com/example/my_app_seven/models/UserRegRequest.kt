package com.example.my_app_seven.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserRegRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password2")
    val password2: String
)
