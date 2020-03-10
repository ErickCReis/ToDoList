package com.example.todolist.view.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.todolist.R
import com.example.todolist.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_add.*

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment(), AddView {

    private lateinit var presenterAdd: AddPresenter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterAdd = getPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_toolbar.title = "Nova Tarefa"
        add_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        add_button.setOnClickListener {

        }
    }

    private fun getPresenter(): AddPresenter {
        return AddPresenterImpl()
    }

}
