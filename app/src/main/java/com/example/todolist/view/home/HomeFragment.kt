package com.example.todolist.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeView{

    private lateinit var presenterHome: HomePresenter

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
                    activity!!.onBackPressed()
                }
            }
            true
        }

        floating_button.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
        }


    }

    private fun getPresenter(): HomePresenter {
        return HomePresenterImpl()
    }

}
