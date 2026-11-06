package com.example.agridevelop

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VideoFirebaseRepository {
    private val firestoreDBInstance = FirebaseFirestore.getInstance()

    // 2. Fetch curated farming playlist document references directly from Firebase Firestore Cloud Server
    suspend fun getCuratedFarmingVideos(): List<FarmingVideoItem> {
        Log.d("VideoFirebaseRepository", "Fetching curated farming videos...")
        return try {
            val databaseSnapshot = firestoreDBInstance.collection("farming_videos")
                .get()
                .await()

            val videos = databaseSnapshot.toObjects(FarmingVideoItem::class.java)
            Log.d("VideoFirebaseRepository", "Successfully loaded ${videos.size} farming videos")
            videos
        } catch (e: Exception) {
            Log.e("VideoFirebaseRepository", "Farming videos dynamic list collection load failed: ${e.message}", e)
            emptyList()
        }
    }
}