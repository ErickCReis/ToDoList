package com.example.todolist.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    var uid: String? = "",
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var phone: String? = ""
)