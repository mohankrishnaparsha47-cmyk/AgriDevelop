package com.example.agridevelop

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AgriCloudRepository {
    private val firestoreDBInstance = FirebaseFirestore.getInstance()

    // Query Soil Specific Recommendation Arrays directly from Firebase Firestore Database
    suspend fun getCropsBySoilTypeSelection(selectedSoilType: String): List<String> {
        Log.d("AgriCloudRepository", "Fetching crop recommendations for soilType: $selectedSoilType")
        return try {
            val databaseSnapshot = firestoreDBInstance.collection("crop_recommendations")
                .whereEqualTo("soilType", selectedSoilType)
                .get()
                .await()

            if (!databaseSnapshot.isEmpty) {
                val configurationDoc = databaseSnapshot.documents.firstOrNull()
                // Assuming your field in firebase document table array string tag is "suggestedCropsList"
                val cropsArray = configurationDoc?.get("suggestedCropsList") as? List<String>
                val result = cropsArray ?: emptyList()
                Log.d("AgriCloudRepository", "Successfully fetched ${result.size} crops for soil: $selectedSoilType")
                result
            } else {
                Log.w("AgriCloudRepository", "No crop recommendations found for soil: $selectedSoilType")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("AgriCloudRepository", "Firestore extraction failed: ${e.message}", e)
            emptyList()
        }
    }
}