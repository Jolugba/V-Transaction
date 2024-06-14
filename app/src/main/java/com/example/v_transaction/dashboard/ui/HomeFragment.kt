package com.example.v_transaction.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.dashboard.adapter.AccountAdapter
import com.example.v_transaction.dashboard.viewmodel.HomeViewModel
import com.example.v_transaction.databinding.FragmentHomeBinding
import com.example.v_transaction.util.launchFragment
import com.example.v_transaction.util.observe
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {
    @Inject lateinit var firebaseAuth: FirebaseAuth
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var initial:String


    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         initial = viewModel.getUserDetails()
        greetingText()
        with(binding){
           accountHolderTextView.text=initial
            transferMoney.setOnClickListener {
                launchFragment(HomeFragmentDirections.goToTransferFragment())
            }

        }


    }
    private fun greetingText() {
        val currentHour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val greetingText = when {
            currentHour < 12 -> getString(R.string.good_morning_, initial)
            currentHour < 18 -> getString(R.string.good_afternoon_, initial)
            else -> getString(R.string.good_evening_, initial)
        }
        binding.homeTitle.text = greetingText
    }



    override fun run() {
        observe(viewModel.accounts) {accounts->
            val adapter = AccountAdapter()
            adapter.submitList(accounts)
            binding.userAccountList.adapter = adapter
                }
            }
        }


