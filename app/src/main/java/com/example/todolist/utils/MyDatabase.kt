package com.example.todolist.utils

import android.util.Log
import com.example.todolist.model.ToDo
import com.example.todolist.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

object MyDatabase {

    val database = FirebaseDatabase.getInstance().reference
    var currentUserId: String? = ""
    var toDos: MutableList<ToDo> = mutableListOf()

    // Users Functions

    suspend fun addUser(user: User): Boolean = coroutineScope {

        val key = database.child("users").push().key
        user.uid = key
        currentUserId = key
        database.child("users").child(key!!).setValue(user)

        true
    }

    suspend fun checkUser(email: String): Boolean = coroutineScope {

        database
            .child("users")
            .orderByChild("email")
            .equalTo(email)
            .limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if (dataSnapshot.exists()) {

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            })

        true
    }

    suspend fun getUserId(email: String): Boolean = coroutineScope {

        database
            .child("users")
            .orderByChild("email")
            .equalTo(email)
            .limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.children.forEach {
                            val user = it.getValue(User::class.java)
                            currentUserId = user!!.uid
                            Log.d("GetUserFun", currentUserId!!)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            })

        true
    }

    fun update(user: User) {
        database.child("users").child(currentUserId!!).setValue(user)
    }

    // ToDos Functions

    fun getToDos() {
        toDos.clear()

        database
            .child("toDo")
            .orderByChild(currentUserId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        dataSnapshot.children.forEach {
                            Log.d("GetToDos", it.value.toString())
                            val toDo = it.getValue(ToDo::class.java)
                            toDos.add(toDo!!)
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadUser:onCancelled ${databaseError.toException()}")
                }
            })
    }

    fun addToDo(toDo: ToDo) {
        toDos.add(toDo)
    }

    fun saveToDos() {

        runBlocking { database
            .child("toDo")
            .child(currentUserId!!)
            .setValue(toDos) }

        currentUserId = ""

        toDos.clear()

    }

    fun updateToDo(toDo: ToDo) {

    }
}