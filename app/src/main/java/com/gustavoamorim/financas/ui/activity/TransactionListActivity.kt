package com.gustavoamorim.financas.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.delegate.TransactionDelegate
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import com.gustavoamorim.financas.ui.SummaryView
import com.gustavoamorim.financas.ui.adapter.TransactionListAdapter
import com.gustavoamorim.financas.ui.dialog.AddTransactionDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransactionListActivity : AppCompatActivity() {

    private val transactions: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configSummary()
        configTransactions()
        configFloatActionButton()
    }

    private fun updateTransaction(transaction: Transaction) {
        transactions.add(transaction)
        configSummary()
        configTransactions()
    }

    private fun configSummary() {
        val view: View = window.decorView
        val summaryView = SummaryView(this, view, transactions)
        summaryView.update()
    }

    private fun configTransactions() {
        lista_transacoes_listview.adapter = TransactionListAdapter(transactions, this)
    }

    private fun configFloatActionButton() {
        lista_transacoes_adiciona_receita
            .setOnClickListener { showAddTransactionDialog(TransactionType.INCOME) }
        lista_transacoes_adiciona_despesa
            .setOnClickListener { showAddTransactionDialog(TransactionType.EXPENSE) }
    }

    private fun showAddTransactionDialog(type: TransactionType) {
        AddTransactionDialog(window.decorView as ViewGroup, this)
            .show(type, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    updateTransaction(transaction)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }
}

