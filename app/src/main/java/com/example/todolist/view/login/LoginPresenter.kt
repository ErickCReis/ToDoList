package com.example.todolist.view.login

interface LoginPresenter {
    fun checkLogin(email: String): Boolean
}