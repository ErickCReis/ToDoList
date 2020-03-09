package com.example.todolist.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var phone: String? = "",
    var image: ByteArray?,
    var toDo: MutableList<ToDo>?
)