package com.gustavoamorim.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.model.TransactionType

class AddTransactionDialog(
    viewGroup: ViewGroup,
    context: Context
) : TransactionFormDialog(viewGroup, context) {

    override val positiveButtonText: String
        get() = "Adicionar"
    override val negativeButtonText: String
        get() = "Cancelar"

    override fun getTitleByType(type: TransactionType) = when (type) {
        TransactionType.INCOME -> R.string.adiciona_receita
        TransactionType.EXPENSE -> R.string.adiciona_despesa
    }
}