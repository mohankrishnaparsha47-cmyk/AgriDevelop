package com.example.agridevelop

data class SoilItem(
    val name: String,
    val shortDesc: String,
    val tempRange: String,
    val phLevel: String,
    val waterRetention: String,
    val suitableCrops: List<String>,
    val tips: List<String>,
    val traditionalMethods: List<String>,
    val modernMethods: List<String>,
    val suitableFertilizers: List<String>
)

val sampleSoils = listOf(
    SoilItem(
        name = "Black Soil",
        shortDesc = "Rich in clay content, ideal for cotton, soybean, groundnut.",
        tempRange = "25°C - 35°C",
        phLevel = "7.5 - 8.5",
        waterRetention = "High",
        suitableCrops = listOf("Cotton", "Soybean", "Groundnut", "Wheat", "Sunflower"),
        tips = listOf(
            "Add organic matter like compost or farmyard manure.",
            "Ensure proper drainage to avoid water logging.",
            "Avoid over-irrigation.",
            "Deep ploughing is recommended before sowing."
        ),
        traditionalMethods = listOf(
            "Use of bullock plough for tilling.",
            "Application of compost and green manure.",
            "Crop rotation and mixed cropping."
        ),
        modernMethods = listOf(
            "Drip irrigation to save water.",
            "Use of soil testing kits.",
            "Precision farming and mulching."
        ),
        suitableFertilizers = listOf(
            "Urea (46% N) - For nitrogen",
            "DAP (18-46-0) - For phosphorus",
            "MOP (60% K2O) - For potassium",
            "Compost / Vermicompost - Organic fertilizer"
        )
    ),
    SoilItem(
        name = "Red Soil",
        shortDesc = "Rich in iron oxide, suitable for millets, pulses, oilseeds.",
        tempRange = "22°C - 32°C",
        phLevel = "6.0 - 7.5",
        waterRetention = "Low to Medium",
        suitableCrops = listOf("Ragi", "Groundnut", "Tobacco", "Millets", "Pulses"),
        tips = listOf(
            "Needs regular application of organic fertilizers.",
            "Mulch fields to retain moisture."
        ),
        traditionalMethods = listOf("Organic manuring", "Rainwater catchment"),
        modernMethods = listOf("Micro-irrigation systems", "Zinc/Iron fortification"),
        suitableFertilizers = listOf("NPK balanced mix", "Bio-fertilizers")
    ),
    SoilItem(
        name = "Alluvial Soil",
        shortDesc = "Formed by river deposits, suitable for a wide range of crops.",
        tempRange = "20°C - 30°C",
        phLevel = "6.5 - 8.0",
        waterRetention = "Moderate to High",
        suitableCrops = listOf("Rice", "Wheat", "Sugarcane", "Jute", "Maize"),
        tips = listOf("Maintain soil organic carbon levels."),
        traditionalMethods = listOf("Silt management", "Flooded basin irrigation"),
        modernMethods = listOf("Laser land leveling", "Direct-seeded rice"),
        suitableFertilizers = listOf("Urea", "Superphosphate", "Potash")
    )
)