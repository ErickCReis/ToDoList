package com.example.todolist.view.register

import com.example.todolist.model.User

interface RegisterPresenter {
    fun newUser(user: User)
    fun checkRegister()
}