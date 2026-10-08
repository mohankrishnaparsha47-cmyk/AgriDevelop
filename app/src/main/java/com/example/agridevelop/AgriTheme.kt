package com.example.agridevelop

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AgriTheme {
    // Nature & Forest Accents
    val EmeraldPrimary = Color(0xFF0F5A2C)
    val EmeraldLight = Color(0xFF1B8A46)
    val MintAccent = Color(0xFF34D399)
    val MintBackground = Color(0xFFE8F5E9)

    // Vibrant Modern Color Accents
    val GoldenHarvest = Color(0xFFF59E0B)
    val SunsetOrange = Color(0xFFFF6D00)
    val RiverCyan = Color(0xFF0284C7)
    val RoyalPurple = Color(0xFF7C3AED)
    val SoftRose = Color(0xFFF43F5E)

    // Canvas & Surface Backgrounds
    val CanvasBg = Color(0xFFF4F7F4)
    val CardSurface = Color(0xFFFFFFFF)
    val GlassWhite = Color(0xFFFFFFFF).copy(alpha = 0.88f)
    val BorderSubtle = Color(0xFFE2E8F0)

    // Dynamic Gradients
    val HeaderGradient = Brush.verticalGradient(
        listOf(Color(0xFF062B14), Color(0xFF0F5A2C), Color(0xFF166534))
    )
    val SunriseGlow = Brush.linearGradient(
        listOf(Color(0xFF052E16), Color(0xFF15803D), Color(0xFFCA8A04))
    )
    val CardGleam = Brush.verticalGradient(
        listOf(Color.White, Color(0xFFF8FAF8))
    )
}