package com.example.todolist.view.register

import com.example.todolist.model.User
import com.example.todolist.utils.MyDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class RegisterPresenterImpl (private val database: DatabaseReference): RegisterPresenter {
    override fun newUser(user: User) {

        runBlocking { MyDatabase.addUser(user) }

    }

    override fun checkRegister() {
        TODO("Not yet implemented")
    }

}