package com.example.todolist.view.home

import android.os.Bundle
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
import com.example.todolist.view.home.adapter.ListToDoAdapter
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeView, ListToDoAdapter.OnClickListener{

    private lateinit var presenterHome: HomePresenter
    private lateinit var database: DatabaseReference
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
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment2())
                }
            }
            true
        }

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
        TODO("Not yet implemented")
    }

    private fun getPresenter(): HomePresenter {
        return HomePresenterImpl()
    }

}
