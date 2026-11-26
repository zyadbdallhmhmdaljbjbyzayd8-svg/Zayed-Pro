package com.zayedpro.app.data.model

data class ReminderSettings(
    val enabled: Boolean = true,
    val hour: Int = 9,
    val minute: Int = 0,
    val messageTemplate: String =
        "مرحباً {الاسم}، نذكركم بأن المبلغ المستحق هو {المبلغ} بتاريخ {التاريخ}."
)
