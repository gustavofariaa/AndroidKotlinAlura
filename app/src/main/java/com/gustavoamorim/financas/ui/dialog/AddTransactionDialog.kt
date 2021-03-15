package com.gustavoamorim.financas.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.delegate.TransactionDelegate
import com.gustavoamorim.financas.extension.brazilianFormat
import com.gustavoamorim.financas.extension.toCalendar
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AddTransactionDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val createdView = createLayout()
    private val valueField = createdView.form_transacao_valor
    private val dateField = createdView.form_transacao_data
    private val categoryField = createdView.form_transacao_categoria

    fun show(type: TransactionType, transactionDelegate: TransactionDelegate) {
        configDateField()
        configCategoryField(type)
        configForm(type, transactionDelegate)
    }

    private fun createLayout(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun configDateField() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        dateField.setText(today.brazilianFormat())
        dateField.setOnClickListener {
            DatePickerDialog(context, { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
            }, year, month, day).show()
        }
    }

    private fun configCategoryField(type: TransactionType) {
        val category = when (type) {
            TransactionType.INCOME -> R.array.categorias_de_receita
            TransactionType.EXPENSE -> R.array.categorias_de_despesa
        }

        val adapter = ArrayAdapter.createFromResource(context, category,
                R.layout.support_simple_spinner_dropdown_item)
        categoryField.adapter = adapter
    }

    private fun configForm(type: TransactionType, transactionDelegate: TransactionDelegate) {
        val title = getTitleByType(type)

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton("Adicionar"
            ) { _, _ ->
                val category = categoryField.selectedItem.toString()
                val value = valueField.text.toString()
                val date = dateField.text.toString()

                val currentTransaction = Transaction(
                    type = type,
                    category = category,
                    value = convertValue(value),
                    date = date.toCalendar()
                )
                transactionDelegate.delegate(currentTransaction)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun getTitleByType(type: TransactionType) = when (type) {
        TransactionType.INCOME -> R.string.adiciona_receita
        TransactionType.EXPENSE -> R.string.adiciona_despesa
    }

    private fun convertValue(textValue: String): BigDecimal {
        return try {
            BigDecimal(textValue)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context,
                "Falha na conversão de valor",
                Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }
}