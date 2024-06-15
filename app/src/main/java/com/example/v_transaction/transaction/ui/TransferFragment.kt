package com.example.v_transaction.transaction.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.v_transaction.R
import com.example.v_transaction.base.ViewBindingFragment
import com.example.v_transaction.dashboard.viewmodel.HomeViewModel
import com.example.v_transaction.dashboard.viewmodel.ViewState
import com.example.v_transaction.database.entity.AccountHolder
import com.example.v_transaction.databinding.FragmentTransferBinding
import com.example.v_transaction.util.launchFragment
import com.example.v_transaction.util.observe
import com.example.v_transaction.util.showErrorDialog
import com.example.v_transaction.util.showLongSnackBar
import com.example.v_transaction.util.toggleVisibility
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : ViewBindingFragment<FragmentTransferBinding>() {
    private val viewModel by viewModels<HomeViewModel>()


    override val layoutId: Int
        get() = R.layout.fragment_transfer

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransferBinding{
        return FragmentTransferBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            submitTransferButton.setOnClickListener {
                val sourceAccountId = binding.sourceAccount.selectedItemId.toInt().plus(1)
                val destinationAccountId = binding.destinationAccount.selectedItemId.toInt().plus(1)
                val amount = binding.transferAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
                viewModel.transferMoney(sourceAccountId, destinationAccountId, amount)
            }
    }
    }


 private fun setupSpinners(accounts: List<AccountHolder>) {
        val accountNames = accounts.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accountNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.sourceAccount.adapter = adapter
        binding.destinationAccount.adapter = adapter
    }

    override fun run() {
        observe(viewModel.accounts) {accounts->
            setupSpinners(accounts)
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.loader.root.toggleVisibility(state.isLoading)
                }
                is ViewState.Success -> {
                    showLongSnackBar(state.message)
                    launchFragment(TransferFragmentDirections.goToHomeFragment())
                    Log.e("TransferFragment", "success: ${state.message}")
                }
                is ViewState.Error -> {
                    showErrorDialog(state.errorMessage)
                }
                is ViewState.Confirm -> {
                    // Show a confirmation dialog
                    showConfirmationDialog(state.message)
                }
            }
        }
    }
    private fun showConfirmationDialog(message: String) {
       MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm Transfer")
            .setMessage(message)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                viewModel.onTransferConfirmed()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }



}
