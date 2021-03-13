package com.gustavoamorim.financas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.extension.brazilianFormat
import com.gustavoamorim.financas.extension.limitCharacters
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import kotlinx.android.synthetic.main.transacao_item.view.*

class TransactionListAdapter(
    private val transactions: List<Transaction>,
    private val context: Context
) : BaseAdapter() {

    private val categoryLength = 14

    override fun getCount(): Int {
        return transactions.size
    }

    override fun getItem(position: Int): Transaction {
        return transactions[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val createdView = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)

        val transaction = transactions[position]

        addValue(transaction, createdView)
        addIcon(transaction, createdView)
        addCategory(createdView, transaction)
        addDate(createdView, transaction)

        return createdView
    }

    private fun addDate(createdView: View, transaction: Transaction) {
        createdView.transacao_data.text = transaction.date
            .brazilianFormat()
    }

    private fun addCategory(createdView: View, transaction: Transaction) {
        createdView.transacao_categoria.text = transaction.category
            .limitCharacters(categoryLength)
    }

    private fun addIcon(transaction: Transaction, createdView: View) {
        val icon = getIconByType(transaction.type)
        createdView.transacao_icone.setBackgroundResource(icon)
    }

    private fun getIconByType(type: TransactionType): Int {
        return when (type) {
            TransactionType.INCOME -> R.drawable.icone_transacao_item_receita
            TransactionType.EXPENSE -> R.drawable.icone_transacao_item_despesa
        }
    }

    private fun addValue(transaction: Transaction, createdView: View) {
        val color = getColorByType(transaction.type)
        createdView.transacao_valor.setTextColor(color)
        createdView.transacao_valor.text = transaction.value
            .brazilianFormat()
    }

    private fun getColorByType(type: TransactionType): Int {
        return when (type) {
            TransactionType.INCOME -> ContextCompat
                .getColor(context, R.color.receita)
            TransactionType.EXPENSE -> ContextCompat
                .getColor(context, R.color.despesa)
        }
    }
}