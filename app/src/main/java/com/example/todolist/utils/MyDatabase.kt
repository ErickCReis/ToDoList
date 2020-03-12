package com.example.todolist.utils

import android.util.Log
import androidx.lifecycle.*
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
import kotlin.properties.Delegates

object MyDatabase : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    var currentUserId = MutableLiveData<String>()
    var toDos = MutableLiveData<MutableList<ToDo>>()

    init {
        //currentUserId.value = ""
        toDos.value = mutableListOf()
    }

    // Users Functions

    suspend fun addUser(user: User): Boolean = coroutineScope {

        val key = database.child("users").push().key
        user.uid = key
        currentUserId.value = key

        database
            .child("users")
            .child(key!!)
            .setValue(user)

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
                            currentUserId.value = user!!.uid
                            Log.d("GetUserFun", currentUserId.value!!)
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
        database.child("users").child(currentUserId.value!!).setValue(user)
    }

    // ToDos Functions

    fun getToDos() {

        Log.d("GetTodo", currentUserId.toString())



        database
            .child("toDo")
            .child(currentUserId.value!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        val temp: MutableList<ToDo> = mutableListOf()
                        dataSnapshot.children.forEach {
                            Log.d("ToDoResponse", it.value.toString())
                            val toDo = it.getValue(ToDo::class.java)
                            temp.add(toDo!!)
                        }
                        toDos.postValue(temp)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadUser:onCancelled ${databaseError.toException()}")
                }
            })
    }

    fun addToDo(toDo: ToDo) {
        Log.d("Database", "addToDo: $toDo")


        val temp = toDos.value!!
        temp.add(toDo)

        Log.d("Database", "addToDoTemp: $temp")

        //toDos.value!!.clear()
        //toDos.value!!.add(toDo)
        toDos.postValue(temp)

        Log.d("Database", "ToDo: ${toDos.value}")

        //toDos.postValue(temp)

        saveToDos()
    }

    fun saveToDos() {
        Log.d("Database", "Save")

        database
            .child("toDo")
            .child(currentUserId.value!!)
            .setValue(toDos.value)

    }

    fun updateToDo(toDo: ToDo) {

    }

    fun clear() {
        toDos.value!!.clear()
        //currentUserId.value = ""

    }
}