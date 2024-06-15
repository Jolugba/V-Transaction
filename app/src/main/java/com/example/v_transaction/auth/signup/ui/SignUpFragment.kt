package com.example.v_transaction.auth.signup.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.auth.login.model.ViewState
import com.example.v_transaction.auth.signup.viewmodel.SignUpViewModel
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.databinding.SignupFragmentBinding
import com.example.v_transaction.util.ValidationUtils
import com.example.v_transaction.util.launchFragment
import com.example.v_transaction.util.observe
import com.example.v_transaction.util.showErrorDialog
import com.example.v_transaction.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : ViewBindingFragment<SignupFragmentBinding>() {

    private val viewModel by viewModels<SignUpViewModel>()
    override val layoutId: Int
        get() = R.layout.signup_fragment

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): SignupFragmentBinding {
        return SignupFragmentBinding.inflate(inflater, container, false)
    }

    override fun run() {
        setupTextWatchers()
        observe(viewModel.state) { state ->
            when (state) {
                is ViewState.USER -> {
                    launchFragment(R.id.mainDest)
                }

                is ViewState.ERROR -> {
                    binding.emailInputLayout.error = null
                    binding.passwordInputLayout.error = null

                    when (state.type) {
                        "email" -> binding.emailInputLayout.error = state.errorMessage
                        "password" -> binding.passwordInputLayout.error = state.errorMessage
                        else -> {
                            showErrorDialog(state.errorMessage)
                        }
                    }
                }

                is ViewState.LOADING -> {
                    binding.loader.root.toggleVisibility(state.loading)
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run()
        with(binding) {
            signIn.setOnClickListener {
                launchFragment(SignUpFragmentDirections.goToLogin())
            }
            signUpButton.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.signUp(email = email, password = password)
            }

        }
    }

    private fun setupTextWatchers() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.emailInputLayout.error = null
                ValidationUtils.validateFields(email = s.toString(),
                    password = binding.passwordEditText.text.toString(),
                    stateSetter = { viewModel.state.value = it })
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.passwordInputLayout.error = null
                ValidationUtils.validateFields(email = binding.emailEditText.text.toString(),
                    password = s.toString(),
                    stateSetter = { viewModel.state.value = it })
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

}


