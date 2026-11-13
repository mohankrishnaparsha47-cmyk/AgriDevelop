package com.example.agridevelop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AgriDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "agridevelop.db"
        private const val DATABASE_VERSION = 2

        // Farmers Table
        const val TABLE_FARMERS = "farmers"
        const val COL_MOBILE = "mobile"
        const val COL_NAME = "name"
        const val COL_PASSWORD = "password"
        const val COL_LAND_SIZE = "land_size"
        const val COL_LOCATION = "location"

        // Active Session Table
        const val TABLE_SESSION = "session"
        const val COL_SESSION_ID = "id"
        const val COL_ACTIVE_MOBILE = "active_mobile"
        const val COL_ACTIVE_NAME = "active_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Farmers table
        db.execSQL(
            """
            CREATE TABLE $TABLE_FARMERS (
                $COL_MOBILE TEXT PRIMARY KEY,
                $COL_NAME TEXT,
                $COL_PASSWORD TEXT,
                $COL_LAND_SIZE TEXT,
                $COL_LOCATION TEXT
            )
            """.trimIndent()
        )

        // Create Session table (stores active session)
        db.execSQL(
            """
            CREATE TABLE $TABLE_SESSION (
                $COL_SESSION_ID INTEGER PRIMARY KEY,
                $COL_ACTIVE_MOBILE TEXT,
                $COL_ACTIVE_NAME TEXT
            )
            """.trimIndent()
        )

        // Sample farmer for offline testing
        val cv = ContentValues().apply {
            put(COL_MOBILE, "9876543210")
            put(COL_NAME, "Ramesh Kumar")
            put(COL_PASSWORD, "1234")
            put(COL_LAND_SIZE, "5.0")
            put(COL_LOCATION, "Nalgonda, Telangana")
        }
        db.insert(TABLE_FARMERS, null, cv)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FARMERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SESSION")
        onCreate(db)
    }

    // ================= FARMER AUTH OPERATIONS =================

    fun registerFarmer(
        mobile: String,
        name: String,
        pass: String,
        landSize: String = "",
        location: String = ""
    ): Boolean {
        val db = writableDatabase
        return try {
            val cv = ContentValues().apply {
                put(COL_MOBILE, mobile.trim())
                put(COL_NAME, name.trim())
                put(COL_PASSWORD, pass.trim())
                put(COL_LAND_SIZE, landSize.trim())
                put(COL_LOCATION, location.trim())
            }
            val result = db.insertWithOnConflict(TABLE_FARMERS, null, cv, SQLiteDatabase.CONFLICT_FAIL)
            result != -1L
        } catch (_: Exception) {
            false
        }
    }

    fun authenticateFarmer(mobile: String, pass: String): Pair<String, String>? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_MOBILE, $COL_NAME FROM $TABLE_FARMERS WHERE $COL_MOBILE = ? AND $COL_PASSWORD = ?",
            arrayOf(mobile.trim(), pass.trim())
        )
        return cursor.use {
            if (it.moveToFirst()) {
                val foundMobile = it.getString(it.getColumnIndexOrThrow(COL_MOBILE))
                val foundName = it.getString(it.getColumnIndexOrThrow(COL_NAME))
                Pair(foundName, foundMobile)
            } else {
                null
            }
        }
    }

    fun getUserName(mobile: String): String? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_NAME FROM $TABLE_FARMERS WHERE $COL_MOBILE = ?",
            arrayOf(mobile.trim())
        )
        return cursor.use {
            if (it.moveToFirst()) {
                it.getString(it.getColumnIndexOrThrow(COL_NAME))
            } else {
                null
            }
        }
    }

    // ================= PERSISTENT SESSION =================

    fun saveActiveSession(mobile: String, name: String) {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_SESSION")
        val cv = ContentValues().apply {
            put(COL_SESSION_ID, 1)
            put(COL_ACTIVE_MOBILE, mobile)
            put(COL_ACTIVE_NAME, name)
        }
        db.insert(TABLE_SESSION, null, cv)
    }

    fun getActiveSession(): Pair<String, String>? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_ACTIVE_MOBILE, $COL_ACTIVE_NAME FROM $TABLE_SESSION WHERE $COL_SESSION_ID = 1",
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                val mobile = it.getString(it.getColumnIndexOrThrow(COL_ACTIVE_MOBILE))
                val name = it.getString(it.getColumnIndexOrThrow(COL_ACTIVE_NAME))
                Pair(name, mobile)
            } else {
                null
            }
        }
    }

    fun clearSession() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_SESSION")
    }
}