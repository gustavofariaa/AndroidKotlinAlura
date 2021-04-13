package com.gustavoamorim.financas.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.gustavoamorim.financas.R
import com.gustavoamorim.financas.extension.brazilianFormat
import com.gustavoamorim.financas.extension.toCalendar
import com.gustavoamorim.financas.model.Transaction
import com.gustavoamorim.financas.model.TransactionType
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*


abstract class TransactionFormDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val createdView = createLayout()

    protected val valueField: TextInputEditText = createdView.form_transacao_valor
    protected val dateField: TextInputEditText = createdView.form_transacao_data
    protected val categoryField: Spinner = createdView.form_transacao_categoria

    protected abstract val positiveButtonText: String
    protected abstract val negativeButtonText: String

    fun show(type: TransactionType, delegate: (transition: Transaction) -> Unit) {
        configDateField()
        configCategoryField(type)
        configForm(type, delegate)
    }

    private fun createLayout(): View = LayoutInflater.from(context)
        .inflate(R.layout.form_transacao, viewGroup, false)

    protected fun getCategoryByType(type: TransactionType) = when (type) {
        TransactionType.INCOME -> R.array.categorias_de_receita
        TransactionType.EXPENSE -> R.array.categorias_de_despesa
    }

    protected abstract fun getTitleByType(type: TransactionType): Int

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
                dateField.setText(selectedDate.brazilianFormat())
            }, year, month, day).show()
        }

    }

    private fun configCategoryField(type: TransactionType) {
        val category = getCategoryByType(type)

        val adapter = ArrayAdapter.createFromResource(context, category,
            R.layout.support_simple_spinner_dropdown_item)
        categoryField.adapter = adapter
    }

    private fun configForm(type: TransactionType, delegate: (transition: Transaction) -> Unit) {
        val title = getTitleByType(type)

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton(positiveButtonText) { _, _ ->
                val category = categoryField.selectedItem.toString()
                val value = valueField.text.toString()
                val date = dateField.text.toString()

                val currentTransaction = Transaction(
                    type = type,
                    category = category,
                    value = convertValue(value),
                    date = date.toCalendar()
                )
                delegate(currentTransaction)
            }
            .setNegativeButton(negativeButtonText, null)
            .show()
    }

    private fun convertValue(textValue: String): BigDecimal = try {
        BigDecimal(textValue)
    } catch (exception: NumberFormatException) {
        Toast.makeText(context,
            "Falha na conversão de valor",
            Toast.LENGTH_LONG)
            .show()
        BigDecimal.ZERO
    }
}