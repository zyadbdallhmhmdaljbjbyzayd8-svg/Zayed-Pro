package com.zayedpro.app.data.model

enum class TransactionType {
    DEBT, PAYMENT
}

data class DebtTransaction(
    val id: String = "",
    val customerId: String = "",
    val ownerId: String = "",
    val type: String = TransactionType.DEBT.name,
    val amount: Long = 0,
    val note: String = "",
    val dueAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
