package com.example.todolist.utils

import android.util.Log
import com.example.todolist.model.ToDo
import com.example.todolist.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object MyDatabase {

    val database = FirebaseDatabase.getInstance().reference
    var currentUserId: String? = ""

    fun add(user: User): Boolean {
        val key = database.child("users").push().key
        user.uid = key
        currentUserId = key
        database.child("users").child(key!!).setValue(user)

        return true
    }

    fun getUser(): User? {

        var user: User? = User()

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                Log.d("UpdateUser", user.toString())

            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("users").addValueEventListener(menuListener)



        return user
    }

    fun update(user: User) {
        database.child("users").child(currentUserId!!).setValue(user)
    }

    fun checkUser(email: String): Boolean{

        return true
    }

    fun addToDo(toDo: ToDo) {

        val key = database.child("toDo").push().key

        database.child("toDo")
            .child(currentUserId!!)
            .child(key!!)
            .setValue(toDo)

    }

    fun updateToDo(toDo: ToDo) {

    }
}