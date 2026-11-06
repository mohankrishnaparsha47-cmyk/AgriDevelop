package com.example.agridevelop

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

object FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveFarmer(
        name: String,
        mobile: String,
        land: String,
        location: String
    ) = run {
        require(name.isNotBlank()) { "Name is required" }
        require(mobile.isNotBlank()) { "Mobile number is required" }
        require(land.isNotBlank()) { "Land details are required" }
        require(location.isNotBlank()) { "Location is required" }

        db.collection("Farmers")
            .document(mobile)
            .set(
                hashMapOf(
                    "name" to name.trim(),
                    "mobile" to mobile.trim(),
                    "landSize" to land.trim(),
                    "district" to location.trim(),
                    "createdAt" to FieldValue.serverTimestamp()
                )
            )
    }
}