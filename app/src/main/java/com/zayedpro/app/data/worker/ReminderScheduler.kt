package com.zayedpro.app.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun schedule(context: Context) {
        val request =
            PeriodicWorkRequestBuilder<DueReminderWorker>(
                24, TimeUnit.HOURS
            ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "zayed_pro_due_reminders",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
