package com.gustavoamorim.financas.delegate

import com.gustavoamorim.financas.model.Transaction

interface TransactionDelegate {
    fun delegate(transaction: Transaction)
}