package com.zayedpro.app.data.repository

import com.zayedpro.app.data.model.Customer
import com.zayedpro.app.data.model.DebtTransaction

interface CustomerRepository {
    suspend fun addCustomer(customer: Customer): String
    suspend fun getCustomers(): List<Customer>
    suspend fun addTransaction(transaction: DebtTransaction)
}
