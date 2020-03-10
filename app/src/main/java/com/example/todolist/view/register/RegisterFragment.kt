package com.example.todolist.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.todolist.R
import com.example.todolist.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment(), RegisterView {

    private lateinit var presenterRegister: RegisterPresenter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        presenterRegister = getPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_toolbar.title = "Cadastro"
        register_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        register_button.setOnClickListener {
            val user = User(
                null,
                register_name.text.toString(),
                register_email.text.toString(),
                register_password.text.toString(),
                register_phone.text.toString(),
                null,
                null
            )

            presenterRegister.newUser(user)
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }
    }

    private fun getPresenter(): RegisterPresenter {
        return RegisterPresenterImpl(database)
    }

}
