package com.example.agridevelop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

object DiseaseDetectionRepository {

    private const val BACKEND_URL = "https://agridevelop.onrender.com/api/diagnose-leaf"

    private val client = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

    // Wakes up Render free tier in the background
    suspend fun wakeUpServer() {
        withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://agridevelop.onrender.com/")
                    .get()
                    .build()
                client.newCall(request).execute().close()
            } catch (_: Exception) {
            }
        }
    }

    suspend fun detectDisease(
        bitmap: Bitmap,
        language: String = "Telugu"
    ): String {
        return analyzeLeafImage(bitmap = bitmap, language = language)
    }

    suspend fun analyzeLeafImage(
        context: Context? = null,
        imageUri: Uri? = null,
        bitmap: Bitmap? = null,
        language: String = "Telugu"
    ): String {
        return withContext(Dispatchers.IO) {
            try {
                val sourceBitmap = bitmap ?: imageUri?.let { uri ->
                    context?.contentResolver?.openInputStream(uri)?.use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                } ?: return@withContext "దయచేసి ముందుగా ఆకు ఫోటోను ఎంచుకోండి."

                // Compress to ~60 KB JPEG for fast upload
                val compressedBytes = compressBitmap(sourceBitmap)

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "image",
                        "leaf.jpg",
                        compressedBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
                    )
                    .addFormDataPart("language", language)
                    .build()

                val request = Request.Builder()
                    .url(BACKEND_URL)
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string() ?: ""
                    if (response.isSuccessful && bodyStr.isNotEmpty()) {
                        val json = JSONObject(bodyStr)
                        return@withContext json.optString(
                            "diagnosis",
                            "విశ్లేషణ అందుబాటులో లేదు. మళ్లీ ప్రయత్నించండి."
                        )
                    } else {
                        return@withContext "Server Error (${response.code}). Please try again."
                    }
                }
            } catch (e: Exception) {
                return@withContext "Network Timeout: సర్వర్ వేడెక్కుతోంది, దయచేసి 5 సెకన్ల తర్వాత మళ్లీ నొక్కండి."
            }
        }
    }

    private fun compressBitmap(original: Bitmap): ByteArray {
        val maxDim = 640
        val ratio = minOf(
            maxDim.toFloat() / original.width,
            maxDim.toFloat() / original.height
        ).coerceAtMost(1.0f)

        val targetW = (original.width * ratio).toInt().coerceAtLeast(1)
        val targetH = (original.height * ratio).toInt().coerceAtLeast(1)

        val resized = Bitmap.createScaledBitmap(original, targetW, targetH, true)
        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 75, stream)
        return stream.toByteArray()
    }
}