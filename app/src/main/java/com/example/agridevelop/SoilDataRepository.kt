package com.example.agridevelop

data class Soil(
    val id: String,
    val nameEn: String,
    val nameTe: String,
    val descriptionEn: String,
    val phRange: String,
    val moistureHold: String,
    val recommendedCrops: List<String>
)

object SoilDataRepository {
    val soils = listOf(
        Soil(
            id = "black_soil",
            nameEn = "Black Soil",
            nameTe = "నల్ల రేగడి నేలలు",
            descriptionEn = "Rich in clay content, ideal for cotton, soybean, groundnut.",
            phRange = "7.5 - 8.5",
            moistureHold = "High",
            recommendedCrops = listOf("Cotton", "Soybean", "Wheat", "Jowar")
        ),
        Soil(
            id = "red_soil",
            nameEn = "Red Soil",
            nameTe = "ఎర్ర నేలలు",
            descriptionEn = "Rich in iron oxide, suitable for millets, pulses, oilseeds.",
            phRange = "6.0 - 7.5",
            moistureHold = "Low to Medium",
            recommendedCrops = listOf("Groundnut", "Castor", "Red Gram", "Millets")
        ),
        Soil(
            id = "alluvial_soil",
            nameEn = "Alluvial Soil",
            nameTe = "ఒండ్రు నేలలు",
            descriptionEn = "Formed by river deposits, suitable for a wide range of crops.",
            phRange = "6.5 - 8.0",
            moistureHold = "Moderate to High",
            recommendedCrops = listOf("Paddy", "Sugarcane", "Banana", "Vegetables")
        ),
        Soil(
            id = "sandy_soil",
            nameEn = "Sandy Soil",
            nameTe = "ఇసుక నేలలు",
            descriptionEn = "Light textured, low water holding capacity, good for groundnut.",
            phRange = "5.5 - 7.0",
            moistureHold = "Low",
            recommendedCrops = listOf("Groundnut", "Watermelon", "Potato")
        ),
        Soil(
            id = "clay_soil",
            nameEn = "Clay Soil",
            nameTe = "బంకమట్టి నేలలు",
            descriptionEn = "High water retention, suitable for paddy, wheat.",
            phRange = "6.0 - 7.5",
            moistureHold = "High",
            recommendedCrops = listOf("Paddy", "Wheat", "Gram")
        ),
        Soil(
            id = "laterite_soil",
            nameEn = "Laterite Soil",
            nameTe = "లేటరైట్ నేలలు",
            descriptionEn = "Low fertility, suitable for tea, coffee, cashew.",
            phRange = "5.0 - 6.0",
            moistureHold = "Low",
            recommendedCrops = listOf("Cashew", "Coconut", "Mango")
        )
    )
}
