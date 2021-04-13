package com.gustavoamorim.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.extension.brazilianFormat
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType

class UpdateTransactionDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : TransactionFormDialog(viewGroup, context) {

    override val positiveButtonText: String
        get() = "Alterar"
    override val negativeButtonText: String
        get() = "Cancelar"

    fun show(transaction: Transaction, delegate: (transition: Transaction) -> Unit) {
        super.show(transaction.type, delegate)
        initializeFields(transaction)
    }

    private fun initializeFields(transaction: Transaction) {
        initializeValueField(transaction)
        initializeDateField(transaction)
        initializeCategoryField(transaction)
    }

    private fun initializeValueField(transaction: Transaction) =
        valueField.setText(transaction.value.toString())

    private fun initializeDateField(transaction: Transaction) =
        dateField.setText(transaction.date.brazilianFormat())

    private fun initializeCategoryField(transaction: Transaction) {
        val categories = context.resources.getStringArray(getCategoryByType(transaction.type))
        val categoryIndex = categories.indexOf(transaction.category)
        categoryField.setSelection(categoryIndex, true)
    }

    override fun getTitleByType(type: TransactionType) = when (type) {
        TransactionType.INCOME -> R.string.adiciona_receita
        TransactionType.EXPENSE -> R.string.adiciona_despesa
    }
}