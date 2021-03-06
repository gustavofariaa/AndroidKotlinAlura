package com.gustavoamorim.financas.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.brazilianFormat(): String {
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))

    return brazilianFormat
        .format(this)
        .replace("R$", "R$ ")
        .replace("-R$ ", "R$ -")
}
