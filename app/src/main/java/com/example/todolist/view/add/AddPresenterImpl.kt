package com.example.todolist.view.add

import com.example.todolist.model.ToDo
import com.example.todolist.utils.MyDatabase

class AddPresenterImpl: AddPresenter {

    override fun addToDo(toDo: ToDo) {
        MyDatabase.addToDo(toDo)
    }

}