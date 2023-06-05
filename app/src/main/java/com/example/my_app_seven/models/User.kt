package com.example.my_app_seven.models

import androidx.room.Entity

@Entity
class User {
    var email: String = ""
    var password: String = ""
    var password_check: String = ""
}