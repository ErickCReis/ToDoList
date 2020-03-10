package com.example.todolist.view.register

import com.example.todolist.model.User
import com.google.firebase.database.DatabaseReference

class RegisterPresenterImpl (private val database: DatabaseReference): RegisterPresenter {
    override fun newUser(user: User) {
        val key = database.child("users").push().key
        user.uId = key
        database.child("users").child(key!!).setValue(user)
    }

    override fun checkRegister() {
        TODO("Not yet implemented")
    }

}