package com.example.todolist.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uId: String? = "",
    val username: String? = "",
    val email: String? = "",
    val password: String? = "",
    val phone: String? = "",
    val image: ByteArray?,
    val toDo: MutableList<ToDo>?
)