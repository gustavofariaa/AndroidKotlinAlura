package com.gustavoamorim.financas.extension

fun String.limitCharacters(stringLength: Int) : String {
    if (this.length > stringLength) {
        val initialPosition = 0
        return "${this.substring(initialPosition, stringLength)}..."
    }
    return this
}
