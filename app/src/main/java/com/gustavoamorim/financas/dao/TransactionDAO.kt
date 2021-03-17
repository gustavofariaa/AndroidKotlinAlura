package com.gustavoamorim.financas.dao

import com.gustavoamorim.financas.model.Transaction

class TransactionDAO {
    val transactions: List<Transaction> = Companion.transactions

    companion object {
        private val transactions: MutableList<Transaction> = mutableListOf()
    }

    fun add(transaction: Transaction) = Companion.transactions
        .add(transaction)

    fun update(transaction: Transaction, position: Int) = Companion.transactions
        .set(position, transaction)

    fun remove(position: Int) = Companion.transactions
        .removeAt(position)
}