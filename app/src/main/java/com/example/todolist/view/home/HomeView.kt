package com.example.todolist.view.home

import com.example.todolist.model.ToDo

interface HomeView {
    fun loadList(listToDo: MutableList<ToDo>)
}