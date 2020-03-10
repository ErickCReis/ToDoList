package com.example.todolist.view.register

import com.example.todolist.model.User
import com.example.todolist.utils.MyDatabase
import com.google.firebase.database.DatabaseReference

class RegisterPresenterImpl (private val database: DatabaseReference): RegisterPresenter {
    override fun newUser(user: User) {
        MyDatabase.checkUser(user.email!!)
        MyDatabase.add(user)
    }

    override fun checkRegister() {
        TODO("Not yet implemented")
    }

}