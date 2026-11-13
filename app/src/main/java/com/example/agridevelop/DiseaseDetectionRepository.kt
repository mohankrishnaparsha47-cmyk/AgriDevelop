package com.example.agridevelop

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.RequestOptions
import com.google.firebase.ai.type.RequestTimeoutException
import com.google.firebase.ai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DiseaseDetectionRepository {

    private const val TAG = "DiseaseDetectionRepo"
    private const val MAX_RETRIES = 2
    private const val INITIAL_RETRY_DELAY_MS = 1000L

    private val model = Firebase.ai(
        backend = GenerativeBackend.googleAI(),
    ).generativeModel(
        modelName = "gemini-3.8-flash",
        requestOptions = RequestOptions(timeoutInMillis = 180_000L),
    )

    suspend fun detectDisease(
        bitmap: Bitmap,
        language: String
    ): String = withContext(Dispatchers.IO) {

        val prompt = """
            You are an agricultural plant disease assistant.

            Analyze the uploaded plant leaf image.

            Provide:
            1. Plant name
            2. Possible disease name
            3. Visible symptoms
            4. Prevention methods
            5. Recommended treatment steps

            Respond in $language language.

            If the image is unclear or not a plant,
            mention that identification is uncertain.

            Do not claim a diagnosis with certainty.
        """.trimIndent()

        var attempt = 0
        var currentDelay = INITIAL_RETRY_DELAY_MS
        var lastException: Exception? = null

        while (attempt <= MAX_RETRIES) {
            attempt++
            try {
                Log.d(TAG, "Detecting disease (attempt $attempt)...")

                val response = model.generateContent(
                    content {
                        text(prompt)
                        image(bitmap)
                    }
                )

                val textResult = response.text
                return@withContext textResult ?: if (language.equals("Telugu", ignoreCase = true)) {
                    "వ్యాధిని గుర్తించలేకపోయాము."
                } else {
                    "Unable to identify the disease."
                }
            } catch (e: RequestTimeoutException) {
                lastException = e
                Log.w(TAG, "Disease detection request timed out on attempt $attempt", e)
                if (attempt <= MAX_RETRIES) {
                    kotlinx.coroutines.delay(currentDelay)
                    currentDelay *= 2
                }
            } catch (e: Exception) {
                lastException = e
                if (attempt <= MAX_RETRIES && isTransientError(e)) {
                    Log.w(TAG, "Transient error during disease detection on attempt $attempt: ${e.message}", e)
                    kotlinx.coroutines.delay(currentDelay)
                    currentDelay *= 2
                } else {
                    Log.e(TAG, "Disease detection failed: ${e.message}", e)
                    throw e
                }
            }
        }

        if (lastException is RequestTimeoutException) {
            Log.e(TAG, "Disease detection request timed out after retries", lastException)
            if (language.equals("Telugu", ignoreCase = true)) {
                "చిత్ర విశ్లేషణ సమయం ముగిసింది. దయచేసి ఇంటర్నెట్ కనెక్షన్ తనిఖీ చేసి మళ్లీ ప్రయత్నించండి."
            } else {
                "Image analysis request timed out. Please check your internet connection and try again."
            }
        } else {
            throw lastException ?: Exception("Unknown error during disease detection")
        }
    }

    private fun isTransientError(e: Exception): Boolean {
        val msg = e.message?.lowercase() ?: ""
        return msg.contains("timeout") || msg.contains("unavailable") || msg.contains("connection")
    }
}