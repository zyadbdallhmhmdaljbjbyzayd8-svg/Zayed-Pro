package com.zayedpro.app.data.model

data class Customer(
    val id: String = "",
    val ownerId: String = "",
    val name: String = "",
    val phone: String = "",
    val governorate: String = "",
    val city: String = "",
    val village: String = "",
    val market: String = "",
    val notes: String = "",
    val balance: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
