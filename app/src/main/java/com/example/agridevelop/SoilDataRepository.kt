package com.example.agridevelop

object SoilDataRepository {

    val soils: List<Soil> = listOf(

        Soil(
            id = "black_soil",
            nameEn = "Black Soil",
            nameTe = "నల్ల రేగడి నేలలు",
            descriptionEn = "Rich in clay content, ideal for cotton, soybean and groundnut.",
            tempRange = "25°C - 35°C",
            phRange = "7.5 - 8.5",
            moistureHold = "High",
            recommendedCrops = listOf("Cotton", "Soybean", "Groundnut", "Wheat", "Sunflower"),
            tips = listOf("Add organic compost.", "Ensure proper drainage.", "Avoid over-irrigation."),
            traditionalMethods = listOf("Bullock ploughing", "Green manuring", "Crop rotation"),
            modernMethods = listOf("Drip irrigation", "Soil testing", "Precision farming"),
            suitableFertilizers = listOf("Urea", "DAP", "MOP", "Vermicompost")
        ),

        Soil(
            id = "red_soil",
            nameEn = "Red Soil",
            nameTe = "ఎర్ర నేలలు",
            descriptionEn = "Rich in iron oxide, suitable for millets, pulses and oilseeds.",
            tempRange = "22°C - 32°C",
            phRange = "6.0 - 7.5",
            moistureHold = "Low to Medium",
            recommendedCrops = listOf("Ragi", "Groundnut", "Castor", "Red Gram", "Millets"),
            tips = listOf("Apply organic fertilizers.", "Use mulch to retain moisture."),
            traditionalMethods = listOf("Organic manuring", "Rainwater harvesting"),
            modernMethods = listOf("Micro irrigation", "Soil testing"),
            suitableFertilizers = listOf("NPK", "Bio-fertilizers", "Compost")
        ),

        Soil(
            id = "alluvial_soil",
            nameEn = "Alluvial Soil",
            nameTe = "ఒండ్రు నేలలు",
            descriptionEn = "Formed by river deposits and suitable for many crops.",
            tempRange = "20°C - 30°C",
            phRange = "6.5 - 8.0",
            moistureHold = "Moderate to High",
            recommendedCrops = listOf("Rice", "Wheat", "Sugarcane", "Jute", "Maize"),
            tips = listOf("Maintain soil organic carbon.", "Monitor soil nutrients."),
            traditionalMethods = listOf("Silt management", "Basin irrigation"),
            modernMethods = listOf("Laser land leveling", "Direct seeded rice"),
            suitableFertilizers = listOf("Urea", "Superphosphate", "Potash")
        ),

        Soil(
            id = "sandy_soil",
            nameEn = "Sandy Soil",
            nameTe = "ఇసుక నేలలు",
            descriptionEn = "Light textured soil with low water holding capacity.",
            tempRange = "25°C - 38°C",
            phRange = "5.5 - 7.0",
            moistureHold = "Low",
            recommendedCrops = listOf("Groundnut", "Watermelon", "Potato", "Millets"),
            tips = listOf("Add organic matter.", "Use frequent light irrigation."),
            traditionalMethods = listOf("Organic manuring", "Mulching"),
            modernMethods = listOf("Drip irrigation", "Moisture monitoring"),
            suitableFertilizers = listOf("Compost", "NPK", "Bio-fertilizers")
        ),

        Soil(
            id = "clay_soil",
            nameEn = "Clay Soil",
            nameTe = "బంకమట్టి నేలలు",
            descriptionEn = "Fine textured soil with high water retention.",
            tempRange = "20°C - 32°C",
            phRange = "6.0 - 7.5",
            moistureHold = "High",
            recommendedCrops = listOf("Paddy", "Wheat", "Gram", "Lentils"),
            tips = listOf("Ensure proper drainage.", "Avoid working on very wet soil."),
            traditionalMethods = listOf("Ploughing", "Crop rotation"),
            modernMethods = listOf("Drainage systems", "Soil testing"),
            suitableFertilizers = listOf("Urea", "DAP", "Organic manure")
        ),

        Soil(
            id = "laterite_soil",
            nameEn = "Laterite Soil",
            nameTe = "లేటరైట్ నేలలు",
            descriptionEn = "Iron-rich soil with generally low natural fertility.",
            tempRange = "20°C - 30°C",
            phRange = "5.0 - 6.0",
            moistureHold = "Low",
            recommendedCrops = listOf("Cashew", "Coconut", "Mango", "Tea", "Coffee"),
            tips = listOf("Add organic manure.", "Prevent soil erosion."),
            traditionalMethods = listOf("Terrace farming", "Organic manuring"),
            modernMethods = listOf("Contour farming", "Soil testing"),
            suitableFertilizers = listOf("Compost", "NPK", "Lime when soil test recommends")
        )
    )
}