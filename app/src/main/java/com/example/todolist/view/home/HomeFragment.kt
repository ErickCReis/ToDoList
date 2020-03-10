package com.example.todolist.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.ToDo
import com.example.todolist.model.User
import com.example.todolist.utils.MyDatabase
import com.example.todolist.view.home.adapter.ListToDoAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeView, ListToDoAdapter.OnClickListener{

    private lateinit var presenterHome: HomePresenter
    private var adapter: ListToDoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_toolbar.title = "Tarefas"
        home_toolbar.inflateMenu(R.menu.menu)
        home_toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.exit -> {
                    MyDatabase.currentUserId = ""
                    requireActivity().onBackPressed()
                }
            }
            true
        }

        Log.d("Home_MyId", MyDatabase.currentUserId!!)

        MyDatabase.database
            .child("toDo")
            .child(MyDatabase.currentUserId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {

                        val listToDo: MutableList<ToDo> = mutableListOf()
                        for (data in dataSnapshot.children) {
                            val toDo = data.getValue(ToDo::class.java)
                            listToDo.add(toDo!!)
                        }
                        Log.d("Home_response", listToDo.toString())
                        loadList(listToDo)
                    }



                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadUser:onCancelled ${databaseError.toException()}")
                }
            })

        floating_button.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
        }

    }

    override fun loadList(listToDo: MutableList<ToDo>) {
        adapter = ListToDoAdapter(
            listToDo,
            requireContext(),
            this
        )
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun checkToDo(toDo: ToDo) {
        Log.d("CheckBox", toDo.title)
    }

    private fun getPresenter(): HomePresenter {
        return HomePresenterImpl()
    }

}
