package com.gustavoamorim.financas.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.brazilianFormat(): String {
    val brazilianFormat = SimpleDateFormat("dd/MM/yyyy")
    return brazilianFormat.format(this.time)
}
