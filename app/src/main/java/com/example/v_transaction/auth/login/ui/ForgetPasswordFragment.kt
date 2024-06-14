package com.example.v_transaction.auth.login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.auth.login.viewmodel.ForgetPasswordViewState
import com.example.v_transaction.auth.login.viewmodel.ForgotPasswordViewModel
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.databinding.FragmentForgetPasswordBinding
import com.example.v_transaction.util.launchFragment
import com.example.v_transaction.util.observe
import com.example.v_transaction.util.showErrorDialog
import com.example.v_transaction.util.showLongSnackBar
import com.example.v_transaction.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : ViewBindingFragment<FragmentForgetPasswordBinding>() {

    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override val layoutId: Int
        get() = R.layout.fragment_forget_password

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentForgetPasswordBinding {
        return FragmentForgetPasswordBinding.inflate(inflater, container, false)
    }

    override fun run() {
        observe(viewModel.state) { state ->
            when (state) {
                is ForgetPasswordViewState.LOADING -> {
                    binding.loader.root.toggleVisibility(state.isLoading)
                }

                is ForgetPasswordViewState.SUCCESS -> {
                    showLongSnackBar(state.message)
                    launchFragment(R.id.action_forgetPasswordFragment_to_login_dest)
                }

                is ForgetPasswordViewState.ERROR -> {
                    if (state.type == "email") {
                        binding.emailInputLayout.error = state.errorMessage
                    } else {
                        showErrorDialog(state.errorMessage)
                    }
                }
                ForgetPasswordViewState.CLEAR_ERROR -> {
                    binding.emailInputLayout.error = null
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run()
        with(binding) {
            btnResetLink.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                viewModel.sendPasswordResetEmail(email)
            }
            emailEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    viewModel.validateEmailField(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }


        }

    }





