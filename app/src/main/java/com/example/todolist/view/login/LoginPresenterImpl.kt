package com.example.todolist.view.login

import android.util.Log
import com.example.todolist.utils.MyDatabase
import kotlinx.coroutines.runBlocking

class LoginPresenterImpl : LoginPresenter{

    override fun checkLogin(email: String): Boolean{

        runBlocking { MyDatabase.getUserId(email) }

        return true
    }
}