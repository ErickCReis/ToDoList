package com.example.todolist.view.home

import com.example.todolist.utils.MyDatabase

class HomePresenterImpl: HomePresenter {

    override fun getToDo() {
        MyDatabase.getToDos()
    }

    override fun saveToDos() {
        MyDatabase.saveToDos()
    }
}