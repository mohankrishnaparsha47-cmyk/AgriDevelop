package com.example.agridevelop

import android.content.Context

data class User(
    val mobile: String,
    val fullName: String
)

object AuthRepository {
    private var dbHelper: AgriDatabaseHelper? = null

    fun init(context: Context) {
        if (dbHelper == null) {
            dbHelper = AgriDatabaseHelper(context.applicationContext)
        }
    }

    fun register(mobile: String, name: String, pass: String): Boolean {
        return dbHelper?.registerFarmer(mobile, name, pass) ?: false
    }

    fun authenticate(mobile: String, pass: String): User? {
        val record = dbHelper?.authenticateFarmer(mobile, pass) ?: return null
        // Save session so user stays logged in
        dbHelper?.saveActiveSession(record.second, record.first)
        return User(mobile = record.second, fullName = record.first)
    }

    fun getSavedSession(): User? {
        val session = dbHelper?.getActiveSession() ?: return null
        return User(mobile = session.second, fullName = session.first)
    }

    fun logout() {
        dbHelper?.clearSession()
    }
}