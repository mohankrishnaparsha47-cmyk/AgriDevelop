package com.example.agridevelop

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

data class MandiPrice(
    val cropName: String,
    val market: String,
    val minPrice: String,
    val modalPrice: String,
    val maxPrice: String,
    val unit: String,
    val trend: String
)

object AgriAiClient {
    // Configured for your laptop IP address
    private const val BASE_URL = "http://10.177.4.50:8000"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    /**
     * Text / Voice Chat via FastAPI backend
     */
    suspend fun getResponse(userMessage: String): String = withContext(Dispatchers.IO) {
        try {
            val jsonPayload = JSONObject().apply {
                put("message", userMessage)
            }

            val requestBody = jsonPayload.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url("$BASE_URL/api/chat")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string().orEmpty()

            if (response.isSuccessful) {
                val json = JSONObject(body)
                return@withContext json.optString("reply", "No response received.")
            } else {
                return@withContext "Server error (${response.code}): $body"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext "Connection error: ${e.localizedMessage ?: "Unable to reach server"}"
        }
    }

    /**
     * Crop Doctor Vision Diagnosis: Pre-scales image to ~1024px to ensure fast upload
     */
    suspend fun analyzeCropLeaf(bitmap: Bitmap, isTelugu: Boolean): String = withContext(Dispatchers.IO) {
        try {
            val maxDimension = 1024
            val width = bitmap.width
            val height = bitmap.height
            val scaledBitmap = if (width > maxDimension || height > maxDimension) {
                val ratio = width.toFloat() / height.toFloat()
                val targetW = if (ratio > 1) maxDimension else (maxDimension * ratio).toInt()
                val targetH = if (ratio > 1) (maxDimension / ratio).toInt() else maxDimension
                Bitmap.createScaledBitmap(bitmap, targetW, targetH, true)
            } else {
                bitmap
            }

            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val imageBytes = outputStream.toByteArray()

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image",
                    "leaf.jpg",
                    imageBytes.toRequestBody("image/jpeg".toMediaType())
                )
                .addFormDataPart("language", if (isTelugu) "Telugu" else "English")
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/api/diagnose-leaf")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string().orEmpty()

            if (response.isSuccessful) {
                val json = JSONObject(body)
                return@withContext json.optString("diagnosis", "No diagnosis returned.")
            } else {
                return@withContext "Server error (${response.code}): $body"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext "Connection error: ${e.localizedMessage ?: "Unable to reach server"}"
        }
    }

    /**
     * Retrieves Mandi prices for the registered district
     */
    suspend fun getDistrictMandiPrices(district: String): List<MandiPrice> = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<MandiPrice>()
        try {
            val request = Request.Builder()
                .url("$BASE_URL/api/mandi-prices?district=$district")
                .get()
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string().orEmpty()

            if (response.isSuccessful) {
                val json = JSONObject(body)
                val pricesArray = json.optJSONArray("prices")
                if (pricesArray != null) {
                    for (i in 0 until pricesArray.length()) {
                        val item = pricesArray.getJSONObject(i)
                        resultList.add(
                            MandiPrice(
                                cropName = item.optString("crop_name"),
                                market = item.optString("market"),
                                minPrice = item.optString("min_price"),
                                modalPrice = item.optString("modal_price"),
                                maxPrice = item.optString("max_price"),
                                unit = item.optString("unit"),
                                trend = item.optString("trend")
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext resultList
    }
}
