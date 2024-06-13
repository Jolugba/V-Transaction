package com.example.v_transaction.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.auth.login.LoginViewModel
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.databinding.SignupFragmentBinding
import com.example.v_transaction.util.launchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : ViewBindingFragment<SignupFragmentBinding>() {

    val viewModel by viewModels<LoginViewModel>()
    override val layoutId: Int
        get() = R.layout.signup_fragment

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SignupFragmentBinding {
        return SignupFragmentBinding.inflate(inflater, container, false)
    }

    override fun run() {
        Log.e("here","I am here")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run()
        with(binding){
            signIn.setOnClickListener {
                launchFragment(SignUpFragmentDirections.goToLogin())
            }
        }

    }
}
