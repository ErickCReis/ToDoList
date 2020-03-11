package com.example.todolist.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.todolist.R
import com.example.todolist.model.User
import com.example.todolist.utils.MyDatabase
import com.google.firebase.database.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginView {

    private lateinit var presenterLogin: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterLogin = getPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {

            Log.d("LoginButton", MyDatabase.currentUserId!!)

            val email = login_email!!.text.toString()
            if (presenterLogin.checkLogin(email)) {

                Observable.just(MyDatabase.currentUserId)
                    .subscribe {
                        Log.d("Obser", MyDatabase.currentUserId)
                        //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment2())
                    }
            }
        }


        login_register.setOnClickListener {

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun getPresenter(): LoginPresenter {
        return LoginPresenterImpl()
    }
}
