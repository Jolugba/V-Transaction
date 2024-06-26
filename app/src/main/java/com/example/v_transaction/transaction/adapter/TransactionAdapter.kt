package com.example.v_transaction.transaction.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.v_transaction.R
import com.example.v_transaction.database.entity.Transaction
import com.example.v_transaction.util.formatDate

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceAccount: TextView = itemView.findViewById(R.id.sourceAccount)
        val destinationAccount: TextView = itemView.findViewById(R.id.destinationAccount)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_history_item,
            parent, false)
        return TransactionViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.sourceAccount.text = "Debit Alert to ${transaction.destinationAccountName}"
        holder.destinationAccount.text = "Credit Alert from ${transaction.sourceAccountName}"
        holder.amount.text ="$ ${transaction.amount}"
        holder.timestamp.text=transaction.timestamp.formatDate()

    }

    override fun getItemCount() = transactions.size
}
