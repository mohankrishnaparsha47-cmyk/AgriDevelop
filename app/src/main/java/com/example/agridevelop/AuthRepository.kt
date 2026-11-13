package com.example.agridevelop

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class FarmerUser(
    val fullName: String,
    val mobile: String
)

object AuthRepository {

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun requireContext(context: Context?): Context {
        return context ?: appContext
        ?: throw IllegalStateException("AuthRepository must be initialized with init(context) or provided a Context.")
    }

    suspend fun authenticateUser(
        context: Context,
        mobile: String,
        pass: String
    ): FarmerUser? {
        return withContext(Dispatchers.IO) {
            try {
                val dbHelper = AgriDatabaseHelper(context)

                // Match credentials against SQLite TABLE_FARMERS
                val authResult = dbHelper.authenticateFarmer(mobile.trim(), pass.trim())

                if (authResult != null) {
                    val (name, registeredMobile) = authResult

                    // Persist session into SQLite TABLE_SESSION
                    dbHelper.saveActiveSession(registeredMobile, name)

                    return@withContext FarmerUser(fullName = name, mobile = registeredMobile)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Strictly return null if user does not exist or password does not match
            return@withContext null
        }
    }

    suspend fun authenticateUser(
        mobile: String,
        pass: String
    ): FarmerUser? {
        return authenticateUser(requireContext(null), mobile, pass)
    }

    fun register(
        mobile: String,
        name: String,
        pass: String,
        landSize: String = "",
        location: String = "",
        context: Context? = null
    ): Boolean {
        val ctx = requireContext(context)
        val dbHelper = AgriDatabaseHelper(ctx)
        val success = dbHelper.registerFarmer(mobile, name, pass, landSize, location)
        if (success) {
            dbHelper.saveActiveSession(mobile, name)
        }
        return success
    }

    fun getSavedSession(context: Context? = null): FarmerUser? {
        val ctx = requireContext(context)
        val dbHelper = AgriDatabaseHelper(ctx)
        val session = dbHelper.getActiveSession()
        return if (session != null) {
            FarmerUser(fullName = session.first, mobile = session.second)
        } else {
            null
        }
    }

    fun getLoggedInFarmer(context: Context? = null): FarmerUser? {
        return getSavedSession(context)
    }

    fun logout(context: Context? = null) {
        val ctx = requireContext(context)
        val dbHelper = AgriDatabaseHelper(ctx)
        dbHelper.clearSession()
        val prefs = ctx.getSharedPreferences("AgriDevelopPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}
