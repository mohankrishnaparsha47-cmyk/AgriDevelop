package com.example.agridevelop

data class SchemeItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val portalUrl: String
)

data class VideoItem(
    val title: String,
    val duration: String,
    val category: String,
    val videoUrl: String
)

val sampleSchemes = listOf(
    SchemeItem(
        title = "PM-Kisan Samman Nidhi",
        subtitle = "Income support scheme",
        description = "Direct benefit transfer of ₹6,000 per year in three equal installments to landholding farmer families.",
        portalUrl = "https://pmkisan.gov.in"
    ),
    SchemeItem(
        title = "Pradhan Mantri Fasal Bima Yojana (PMFBY)",
        subtitle = "Comprehensive crop insurance",
        description = "Insurance coverage and financial support to farmers in the event of failure of any of the notified crops.",
        portalUrl = "https://pmfby.gov.in"
    ),
    SchemeItem(
        title = "Soil Health Card Scheme",
        subtitle = "Nutrient status advisory",
        description = "Provides information on nutrient status of soil along with recommendation on appropriate dosage of nutrients.",
        portalUrl = "https://soilhealth.dac.gov.in"
    ),
    SchemeItem(
        title = "Kisan Credit Card (KCC)",
        subtitle = "Credit support",
        description = "Adequate and timely credit support from the banking system for agricultural and other allied needs.",
        portalUrl = "https://myscheme.gov.in"
    ),
    SchemeItem(
        title = "National Agriculture Market (e-NAM)",
        subtitle = "Pan-India electronic trading portal",
        description = "Networks the existing APMC mandis to create a unified national market for agricultural commodities.",
        portalUrl = "https://enam.gov.in"
    )
)

val sampleVideos = listOf(
    VideoItem(
        title = "Modern Drip Irrigation Setup Guide",
        duration = "12:45",
        category = "Water Management",
        videoUrl = "https://www.youtube.com"
    ),
    VideoItem(
        title = "Natural Pest Control & Organic Fertilizers",
        duration = "08:30",
        category = "Organic Care",
        videoUrl = "https://www.youtube.com"
    ),
    VideoItem(
        title = "Crop Rotation Strategies for Kharif Season",
        duration = "15:10",
        category = "Crop Planning",
        videoUrl = "https://www.youtube.com"
    ),
    VideoItem(
        title = "Soil Testing: Step-by-Step Field Guide",
        duration = "10:20",
        category = "Soil Health",
        videoUrl = "https://www.youtube.com"
    )
)