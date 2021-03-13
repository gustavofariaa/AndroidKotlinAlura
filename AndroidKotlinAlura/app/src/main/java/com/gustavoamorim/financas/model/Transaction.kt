package com.gustavoamorim.financas.model

import java.math.BigDecimal
import java.util.Calendar

class Transaction(
    val category: String,
    val type: TransactionType,
    val value: BigDecimal,
    val date: Calendar = Calendar.getInstance()
)