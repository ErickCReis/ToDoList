package com.example.todolist.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.ToDo
import com.example.todolist.utils.MyDatabase
import com.example.todolist.view.home.adapter.ListToDoAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeView, ListToDoAdapter.OnClickListener{

    private lateinit var presenterHome: HomePresenter
    private var adapter: ListToDoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterHome = getPresenter()
        presenterHome.getToDo()
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

        Log.d("Home", "ViewCreated")

        home_toolbar.title = "Tarefas"
        home_toolbar.inflateMenu(R.menu.menu)
        home_toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.exit -> activity!!.onBackPressed()
            }
            true
        }

        //swipe_home.setOnRefreshListener {
        //    swipe_home.isRefreshing = false
        //    loadList(MyDatabase.toDos.value!!)
        //}

        floating_button.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
        }

        MyDatabase.toDos.observe(requireActivity(), Observer {
            Log.d("HomeObeserver", it.toString())
            loadList(it)
        })
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

    override fun checkToDo(state: Boolean, position: Int) {
        MyDatabase.toDos.value!![position].done = state
    }

    override fun onResume() {
        Log.d("Home", "Resume")

        super.onResume()
    }

    override fun onStop() {

        Log.d("Home", "Stop")

        MyDatabase.toDos.removeObservers( requireActivity())

        presenterHome.saveToDos()

        MyDatabase.toDos.value!!.clear()

        super.onStop()
    }

    private fun getPresenter(): HomePresenter {
        return HomePresenterImpl()
    }

}
