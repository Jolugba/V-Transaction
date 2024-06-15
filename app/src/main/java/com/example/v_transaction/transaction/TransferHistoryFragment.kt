package com.example.v_transaction.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.dashboard.viewmodel.HomeViewModel
import com.example.v_transaction.databinding.FragmentTransactionHistoryBinding
import com.example.v_transaction.transaction.adapter.TransactionAdapter
import com.example.v_transaction.util.hide
import com.example.v_transaction.util.observe
import com.example.v_transaction.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferHistoryFragment : ViewBindingFragment<FragmentTransactionHistoryBinding>() {
    private val viewModel by viewModels<HomeViewModel>()


    override val layoutId: Int
        get() = R.layout.fragment_transaction_history

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransactionHistoryBinding{
        return FragmentTransactionHistoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun run() {
        observe(viewModel.transactions){transactions->
            if (transactions.isEmpty()){
                binding.emptyText.show()
                binding.transactionHistoryList.hide()
            }else{
                binding.emptyText.hide()
                binding.transactionHistoryList.show()
           val transactionAdapter = TransactionAdapter(transactions)
            binding.transactionHistoryList.adapter = transactionAdapter
        }}

    }



}
