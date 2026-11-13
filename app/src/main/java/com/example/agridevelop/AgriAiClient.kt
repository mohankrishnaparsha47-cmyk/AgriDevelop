package com.example.agridevelop
import com.google.firebase.ai.type.RequestOptions

import com.google.firebase.Firebase
import android.util.Log
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

import com.google.firebase.ai.type.RequestTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object AgriAiClient {

    private const val TAG = "AgriDevelopAI"
    private const val MAX_RETRIES = 2
    private const val INITIAL_RETRY_DELAY_MS = 1000L

    private val model = Firebase.ai(
        backend = GenerativeBackend.googleAI()
    ).generativeModel(
        modelName = "gemini-3.8-flash",
        requestOptions = RequestOptions(
            timeoutInMillis = 180_000L
        )
    )

    suspend fun getResponse(
        query: String,
        language: String
    ): String = withContext(Dispatchers.IO) {

        val prompt = """
            You are AgriDevelop AI, an agricultural assistant
            designed to help farmers.

            Your responsibilities:
            1. Explain crop cultivation methods.
            2. Identify common crop diseases and their symptoms.
            3. Explain soil types and suitable crops.
            4. Give responsible fertilizer guidance.
            5. Explain pest prevention methods.
            6. Provide irrigation and farming tips.
            7. Explain seasonal crop management.

            IMPORTANT RULES:

            - Always answer in $language language.
            - Use simple language that farmers can understand.
            - Give clear, practical, step-by-step explanations.
            - Keep answers relevant to agriculture.
            - Use headings and bullet points when useful.
            - Never invent agricultural facts.
            - Do not guess fertilizer or pesticide quantities.
            - Explain that dosage depends on soil testing,
              crop stage and product instructions.
            - If information is uncertain, clearly mention it.
            - If a question is unrelated to agriculture,
              politely explain that you are an agricultural assistant.

            User Question:
            $query

            Give a helpful and accurate answer.
        """.trimIndent()

        var attempt = 0
        var currentDelay = INITIAL_RETRY_DELAY_MS
        var lastException: Exception? = null

        while (attempt <= MAX_RETRIES) {
            attempt++
            try {
                Log.d(TAG, "Sending question (attempt $attempt): $query")

                val response = model.generateContent(prompt)
                val answer = response.text?.trim()

                if (answer.isNullOrBlank()) {
                    return@withContext if (language.equals("Telugu", true)) {
                        "క్షమించండి, సమాధానం అందుబాటులో లేదు. మళ్లీ ప్రయత్నించండి."
                    } else {
                        "Sorry, no response was generated. Please try again."
                    }
                } else {
                    Log.d(TAG, "AI response received")
                    return@withContext answer
                }
            } catch (e: RequestTimeoutException) {
                lastException = e
                Log.w(TAG, "Request timed out on attempt $attempt", e)
                if (attempt <= MAX_RETRIES) {
                    delay(currentDelay)
                    currentDelay *= 2
                }
            } catch (e: Exception) {
                lastException = e
                if (attempt <= MAX_RETRIES && isTransientError(e)) {
                    Log.w(TAG, "Transient error on attempt $attempt: ${e.message}", e)
                    delay(currentDelay)
                    currentDelay *= 2
                } else {
                    Log.e(TAG, "Gemini request failed", e)
                    throw e
                }
            }
        }

        if (lastException is RequestTimeoutException) {
            Log.e(TAG, "Request timed out after retries", lastException)
            if (language.equals("Telugu", true)) {
                "AI స్పందించడానికి ఎక్కువ సమయం తీసుకుంటోంది. మళ్లీ ప్రయత్నించండి."
            } else {
                "AI request timed out. Please try again."
            }
        } else {
            throw lastException ?: Exception("Unknown error during AI response generation")
        }
    }
    private fun isTransientError(e: Exception): Boolean {
        val msg = e.message?.lowercase() ?: ""

        return msg.contains("timeout") ||
                msg.contains("unavailable") ||
                msg.contains("connection") ||
                msg.contains("high demand") ||
                msg.contains("resource exhausted") ||
                msg.contains("429") ||
                msg.contains("503") ||
                msg.contains("overloaded")
    }

    }
