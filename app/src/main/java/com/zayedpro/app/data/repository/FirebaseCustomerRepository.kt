package com.zayedpro.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zayedpro.app.data.model.Customer
import com.zayedpro.app.data.model.DebtTransaction
import kotlinx.coroutines.tasks.await

class FirebaseCustomerRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : CustomerRepository {

    private fun uid(): String =
        auth.currentUser?.uid
            ?: throw IllegalStateException("يجب تسجيل الدخول أولاً")

    private fun customers() =
        db.collection("users").document(uid()).collection("customers")

    override suspend fun addCustomer(customer: Customer): String {
        val userId = uid()
        val ref = customers().document()
        val item = customer.copy(
            id = ref.id,
            ownerId = userId,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        ref.set(item).await()
        return ref.id
    }

    override suspend fun getCustomers(): List<Customer> {
        val snapshot = customers()
            .orderBy("name")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(Customer::class.java)
        }
    }

    override suspend fun addTransaction(transaction: DebtTransaction) {
        val userId = uid()
        require(transaction.amount > 0) {
            "يجب أن يكون المبلغ أكبر من صفر"
        }

        val customerRef = customers().document(transaction.customerId)
        val transactionRef = customerRef.collection("transactions").document()

        db.runTransaction { tx ->
            val customerSnapshot = tx.get(customerRef)
            require(customerSnapshot.exists()) {
                "العميل غير موجود"
            }

            val oldBalance = customerSnapshot.getLong("balance") ?: 0L
            val newBalance = when (transaction.type) {
                "DEBT" -> Math.addExact(oldBalance, transaction.amount)
                "PAYMENT" -> Math.subtractExact(oldBalance, transaction.amount)
                else -> throw IllegalArgumentException("نوع المعاملة غير صحيح")
            }

            require(newBalance >= 0) {
                "قيمة السداد أكبر من الرصيد المستحق"
            }

            val saved = transaction.copy(
                id = transactionRef.id,
                ownerId = userId,
                createdAt = System.currentTimeMillis()
            )

            tx.set(transactionRef, saved)
            tx.update(
                customerRef,
                mapOf(
                    "balance" to newBalance,
                    "updatedAt" to System.currentTimeMillis()
                )
            )
        }.await()
    }
}
