package com.example.agridevelop

data class Soil(
    val id: String,
    val nameEn: String,
    val nameTe: String,
    val descriptionEn: String,
    val tempRange: String,
    val phRange: String,
    val moistureHold: String,
    val recommendedCrops: List<String>,
    val tips: List<String>,
    val traditionalMethods: List<String>,
    val modernMethods: List<String>,
    val suitableFertilizers: List<String>
)

// Firebase document field mapping model
data class SoilCropRecommendation(
    val soilType: String = "",
    val suggestedCropsList: List<String> = emptyList()
)
