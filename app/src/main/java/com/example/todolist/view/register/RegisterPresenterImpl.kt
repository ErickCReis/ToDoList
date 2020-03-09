package com.example.todolist.view.register

import com.example.todolist.model.User
import com.google.firebase.database.DatabaseReference

class RegisterPresenterImpl (private val database: DatabaseReference): RegisterPresenter {
    override fun newUser(user: User) {
        database.child("users").setValue(user)
    }

    override fun checkRegister() {
        TODO("Not yet implemented")
    }

}