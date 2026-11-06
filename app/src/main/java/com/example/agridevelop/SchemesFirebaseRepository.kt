package com.example.agridevelop

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SchemesFirebaseRepository {
    private val firestoreDBInstance = FirebaseFirestore.getInstance()

    suspend fun getAvailableGovtSchemes(): List<GovtSchemeItem> {
        Log.d("SchemesFirebaseRepository", "Fetching available government schemes...")
        return try {
            val databaseSnapshot = firestoreDBInstance.collection("government_schemes")
                .get()
                .await()

            val schemes = databaseSnapshot.toObjects(GovtSchemeItem::class.java)
            Log.d("SchemesFirebaseRepository", "Successfully loaded ${schemes.size} government schemes")
            schemes
        } catch (e: Exception) {
            Log.e("SchemesFirebaseRepository", "Government schemes collection load failed: ${e.message}", e)
            emptyList()
        }
    }
}
