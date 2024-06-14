package com.example.v_transaction.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.v_transaction.database.entity.AccountHolder
import com.example.v_transaction.databinding.AccountHolderItemBinding
import com.example.v_transaction.util.loadCircleImageFromUri


class AccountAdapter : ListAdapter<AccountHolder, AccountAdapter.AccountViewHolder>(AccountDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = AccountHolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    class AccountViewHolder(private val binding: AccountHolderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: AccountHolder) = with(binding){
            userName.text = account.name
            userImage.loadCircleImageFromUri(account.profileImageResId)
            accountBalance.text = "$ ${account.balance}".format("%.2f")
            accountNumber.text="Acc.no ${account.accountNumber}"
        }
    }


    class AccountDiffCallback : DiffUtil.ItemCallback<AccountHolder>() {
        override fun areItemsTheSame(oldItem: AccountHolder, newItem: AccountHolder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountHolder, newItem: AccountHolder): Boolean {
            return oldItem == newItem
        }
    }
}
