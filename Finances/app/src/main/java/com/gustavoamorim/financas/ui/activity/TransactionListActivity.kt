package com.gustavoamorim.financas.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.dao.TransactionDAO
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import com.gustavoamorim.financas.ui.SummaryView
import com.gustavoamorim.financas.ui.adapter.TransactionListAdapter
import com.gustavoamorim.financas.ui.dialog.AddTransactionDialog
import com.gustavoamorim.financas.ui.dialog.UpdateTransactionDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransactionListActivity : AppCompatActivity() {

    private val dao = TransactionDAO()
    private val transactions = dao.transactions

    private val activityView by lazy { window.decorView }
    private val activityViewGroup by lazy { activityView as ViewGroup }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configSummary()
        configTransactions()
        configFloatActionButton()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        if (menuId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position
            remove(position)
        }
        return super.onContextItemSelected(item)
    }

    private fun showAddTransactionDialog(type: TransactionType) {
        lista_transacoes_adiciona_menu.close(true)
        AddTransactionDialog(activityViewGroup, this)
            .show(type) { createdTransaction -> add(createdTransaction) }
    }

    private fun showUpdateTransactionDialog(transaction: Transaction, position: Int) {
        UpdateTransactionDialog(activityViewGroup, this)
            .show(transaction) { updatedTransaction -> update(updatedTransaction, position) }
    }

    private fun configSummary() {
        val summaryView = SummaryView(this, activityView, transactions)
        summaryView.update()
    }

    private fun configTransactions() {
        val transactionListAdapter = TransactionListAdapter(transactions, this)
        with(lista_transacoes_listview) {
            adapter = transactionListAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                showUpdateTransactionDialog(transaction, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    private fun configFloatActionButton() {
        lista_transacoes_adiciona_receita
            .setOnClickListener { showAddTransactionDialog(TransactionType.INCOME) }
        lista_transacoes_adiciona_despesa
            .setOnClickListener { showAddTransactionDialog(TransactionType.EXPENSE) }
    }

    private fun updateTransactions() {
        configSummary()
        configTransactions()
    }

    private fun add(transaction: Transaction) {
        dao.add(transaction)
        updateTransactions()
    }

    private fun update(transaction: Transaction, position: Int) {
        dao.update(transaction, position)
        updateTransactions()
    }

    private fun remove(position: Int) {
        dao.remove(position)
        updateTransactions()
    }
}

