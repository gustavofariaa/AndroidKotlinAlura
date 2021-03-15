package com.gustavoamorim.financas.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitCharacters(stringLength: Int): String {
    if (this.length > stringLength) {
        val initialPosition = 0
        return "${this.substring(initialPosition, stringLength)}..."
    }
    return this
}

fun String.toCalendar(): Calendar {
    val brazilianFormat = SimpleDateFormat("dd/MM/yyyy")
    val convertedDate = brazilianFormat.parse(this)
    val date = Calendar.getInstance()
    date.time = convertedDate
    return date
}
