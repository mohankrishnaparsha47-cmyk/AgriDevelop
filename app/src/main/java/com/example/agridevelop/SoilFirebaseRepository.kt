package com.example.agridevelop

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SoilFirebaseRepository {

    private val firestoreDBInstance =
        FirebaseFirestore.getInstance()

    suspend fun getCropsBySoilType(
        selectedSoil: String
    ): List<String> {

        return try {

            Log.d(
                "SoilFirebaseRepository",
                "Fetching soil: $selectedSoil"
            )

            val snapshot = firestoreDBInstance
                .collection("soil_details")
                .whereEqualTo("soilName", selectedSoil)
                .get()
                .await()

            val document = snapshot.documents.firstOrNull()

            if (document != null) {

                val crops = document.get("suitableCrops")
                        as? List<*>

                val result = crops
                    ?.filterIsInstance<String>()
                    ?: emptyList()

                Log.d(
                    "SoilFirebaseRepository",
                    "Fetched crops: $result"
                )

                result

            } else {

                Log.w(
                    "SoilFirebaseRepository",
                    "Soil not found: $selectedSoil"
                )

                emptyList()
            }

        } catch (e: Exception) {

            Log.e(
                "SoilFirebaseRepository",
                "Firestore error",
                e
            )

            emptyList()
        }
    }
}