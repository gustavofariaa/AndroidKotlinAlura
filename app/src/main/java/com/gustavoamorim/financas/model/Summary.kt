package com.gustavoamorim.financas.model

import java.math.BigDecimal
import java.util.Calendar

class Summary(private val transactions: List<Transaction>) {

    val income get() = calculate(TransactionType.INCOME)

    val expense get() = calculate(TransactionType.EXPENSE)

    val total get() = income.subtract(expense)!!

    private fun calculate(type: TransactionType): BigDecimal {
        val total: Double = transactions
            .filter { it.type === type }
            .sumByDouble { it.value.toDouble() }
        return BigDecimal(total)
    }
}
