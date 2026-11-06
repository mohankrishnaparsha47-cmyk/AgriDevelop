package com.example.agridevelop

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log

class SoilFirebaseRepository {
    private val firestoreDBInstance = FirebaseFirestore.getInstance()

    suspend fun getCropsBySoilType(selectedSoil: String): List<String> {
        Log.d("SoilFirebaseRepository", "Fetching crop recommendations for soilType: $selectedSoil")
        return try {
            val databaseSnapshot = firestoreDBInstance.collection("crop_recommendations")
                .whereEqualTo("soilType", selectedSoil)
                .get()
                .await()

            if (!databaseSnapshot.isEmpty) {
                val document = databaseSnapshot.documents.firstOrNull()
                // Direct parsing conversion to prevent java class constructor exceptions
                val cropsArray = document?.get("suggestedCropsList") as? List<String>
                val result = cropsArray ?: emptyList()
                Log.d("SoilFirebaseRepository", "Successfully fetched ${result.size} crops for soil: $selectedSoil")
                result
            } else {
                Log.w("SoilFirebaseRepository", "No crop recommendations found for soil: $selectedSoil")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("SoilFirebaseRepository", "Firestore fetch failed: ${e.message}", e)
            emptyList()
        }
    }
}