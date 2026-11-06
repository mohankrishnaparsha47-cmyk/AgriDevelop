@file:Suppress("unused")

package com.example.agridevelop

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AgriAppNetworkEndpoints {

    @GET("data/2.5/weather")
    suspend fun fetchCurrentLocationWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") targetMetricSystem: String = "metric",
    ): WeatherResponse

    @GET("resource/9ef84268-d588-465a-a308-a864443d0086")
    suspend fun fetchLiveMandiCommodityPrices(
        @Query("api-key") govPortalToken: String,
        @Query("format") responseDataType: String = "json",
        @Query("filters[state]") targetState: String,
        @Query("filters[district]") targetDistrict: String,
    ): MarketPriceResponse
}

object RetrofitInstanceClient {
    private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/"
    private const val GOV_MANDI_API_BASE_URL = "https://data.gov.in/"

    val weatherService: AgriAppNetworkEndpoints by lazy {
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AgriAppNetworkEndpoints::class.java)
    }

    val mandiMarketService: AgriAppNetworkEndpoints by lazy {
        Retrofit.Builder()
            .baseUrl(GOV_MANDI_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AgriAppNetworkEndpoints::class.java)
    }
}
