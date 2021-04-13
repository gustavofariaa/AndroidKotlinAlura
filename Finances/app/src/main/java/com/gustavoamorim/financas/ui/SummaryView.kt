package com.gustavoamorim.financas.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.extension.brazilianFormat
import com.gustavoamorim.financas.model.Summary
import com.gustavoamorim.financas.model.Transaction
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class SummaryView(context: Context, private val view: View, transactions: List<Transaction>) {
    private val summary = Summary(transactions)
    private val incomeColor = ContextCompat.getColor(context, R.color.receita)
    private val expenseColor = ContextCompat.getColor(context, R.color.despesa)

    fun update() {
        addIncome()
        addExpense()
        addTotal()
    }

    private fun addIncome() {
        val totalIncome = summary.income
        with(view.resumo_card_receita) {
            setTextColor(incomeColor)
            text = totalIncome.brazilianFormat()
        }
    }

    private fun addExpense() {
        val totalExpense = summary.expense
        with(view.resumo_card_despesa) {
            setTextColor(expenseColor)
            text = totalExpense.brazilianFormat()
        }
    }

    private fun addTotal() {
        val total = summary.total
        val color = if (total >= BigDecimal.ZERO) incomeColor else expenseColor
        with(view.resumo_card_total) {
            setTextColor(color)
            text = total.brazilianFormat()
        }
    }

}