package com.example.agridevelop

data class WeatherResponse(
    val main: MainData,
    val weather: List<WeatherDetails>,
    val name: String
)
data class MainData(val temp: Double, val humidity: Int)
data class WeatherDetails(val main: String, val description: String)

data class MarketPriceResponse(
    val records: List<MandiPriceRecord>
)
data class MandiPriceRecord(
    val state: String,
    val district: String,
    val market: String,
    val commodity: String,
    val variety: String,
    val arrival_date: String,
    val min_price: String,
    val max_price: String,
    val modal_price: String
)