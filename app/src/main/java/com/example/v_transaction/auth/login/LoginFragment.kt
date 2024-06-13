package com.example.v_transaction.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.databinding.LoginFragmentBinding
import com.example.v_transaction.util.launchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : ViewBindingFragment<LoginFragmentBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override val layoutId: Int
        get() = R.layout.login_fragment

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(inflater, container, false)
    }

    override fun run() {
       Log.e("here","I am here")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run()
        with(binding) {
              textSignup.setOnClickListener {
                  launchFragment(LoginFragmentDirections.goToSignUp())
              }
            }

        }
    }



