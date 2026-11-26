package com.zayedpro.app.core

import android.content.Context
import com.google.firebase.FirebaseApp

object FirebaseStatus {
    fun isConfigured(context: Context): Boolean {
        return try {
            FirebaseApp.getApps(context).isNotEmpty()
        } catch (_: Exception) {
            false
        }
    }
}
