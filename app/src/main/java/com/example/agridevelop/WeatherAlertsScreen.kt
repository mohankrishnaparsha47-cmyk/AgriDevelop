package com.example.agridevelop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAlertsScreen(onBack: () -> Unit) {
    val primaryGreen = Color(0xFF256B38)
    val coroutineScope = rememberCoroutineScope()

    var temperature by remember { mutableStateOf("31.0") }
    var windSpeed by remember { mutableStateOf("12.0") }
    var condition by remember { mutableStateOf("Clear Sky") }
    var isLoading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("Live Weather Updates") }

    fun fetchLiveWeather() {
        isLoading = true
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val endpoint = "https://api.open-meteo.com/v1/forecast?latitude=17.0574&longitude=79.2684&current_weather=true"
                    val url = URL(endpoint)
                    val conn = (url.openConnection() as HttpURLConnection).apply {
                        requestMethod = "GET"
                        connectTimeout = 5000
                        readTimeout = 5000
                    }

                    if (conn.responseCode == 200) {
                        val reader = BufferedReader(InputStreamReader(conn.inputStream))
                        val text = reader.readText()
                        reader.close()
                        conn.disconnect()

                        val json = JSONObject(text)
                        val current = json.optJSONObject("current_weather")
                        val temp = current?.optDouble("temperature", 31.0) ?: 31.0
                        val wind = current?.optDouble("windspeed", 12.0) ?: 12.0
                        val code = current?.optInt("weathercode", 0) ?: 0

                        val cond = when (code) {
                            0 -> "Clear Sky"
                            1, 2, 3 -> "Partly Cloudy"
                            51, 53, 55, 61, 63 -> "Rainy / Showers"
                            else -> "Overcast"
                        }
                        Triple(temp.toString(), wind.toString(), cond)
                    } else {
                        null
                    }
                }

                if (result != null) {
                    temperature = result.first
                    windSpeed = result.second
                    condition = result.third
                    statusMessage = "Updated Live Just Now"
                } else {
                    statusMessage = "Offline (Showing Cached Data)"
                }
            } catch (e: Throwable) {
                statusMessage = "Offline (Network Unavailable)"
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchLiveWeather()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather & Advisory", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { fetchLiveWeather() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryGreen)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Weather Display Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0288D1)),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(statusMessage, color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                            Text("$temperature°C", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                            Text(condition, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(36.dp))
                        } else {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = null,
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(54.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Wind Speed: $windSpeed km/h", color = Color.White, fontSize = 13.sp)
                        Text("Region: Telangana", color = Color.White, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Irrigation Advisory", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Today's Watering Advice", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = primaryGreen)
                    Spacer(modifier = Modifier.height(6.dp))
                    val tempVal = temperature.toDoubleOrNull() ?: 30.0
                    val advice = if (tempVal >= 32.0) {
                        "High temperature detected ($temperature°C). Avoid midday watering to prevent evaporation loss. Water early morning or after sunset."
                    } else if (condition.contains("Rain")) {
                        "Rainy conditions detected. Delay field irrigation to avoid waterlogging."
                    } else {
                        "Moderate weather. Standard drip/furrow schedule recommended."
                    }
                    Text(advice, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp)
                }
            }
        }
    }
}