package com.gustavoamorim.financas.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import com.gustavoamorim.financas.ui.adapter.TransactionListAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar

class TransactionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transactions : List<Transaction> = sampleTransactions()
        configTransactions(transactions)
    }

    private fun configTransactions(transactions: List<Transaction>) {
        lista_transacoes_listview.adapter = TransactionListAdapter(transactions, this)
    }

    private fun sampleTransactions() = listOf(
        Transaction(
            category = "Comida",
            type = TransactionType.EXPENSE,
            value = BigDecimal(20.5)
        ),
        Transaction(
            category = "Economia",
            type = TransactionType.INCOME,
            value = BigDecimal(100.0)
        )
    )
}