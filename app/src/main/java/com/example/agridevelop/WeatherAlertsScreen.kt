package com.example.agridevelop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HourlyForecast(
    val time: String,
    val temp: String,
    val icon: ImageVector,
    val rainProb: String
)

data class DailyForecast(
    val day: String,
    val condition: String,
    val tempRange: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAlertsScreen(
    locationName: String = "Nalgonda, Telangana",
    onBack: () -> Unit
) {
    val districtShort = locationName.substringBefore(",")
    val forestGreen = Color(0xFF0F5A2C)
    val sunnyAmber = Color(0xFFF59E0B)
    val skyBlue = Color(0xFF0284C7)

    // Offline simulated forecast based on district
    val hourlyData = remember(locationName) {
        listOf(
            HourlyForecast("Now", "31°C", Icons.Default.WbSunny, "10%"),
            HourlyForecast("12 PM", "33°C", Icons.Default.WbSunny, "15%"),
            HourlyForecast("3 PM", "32°C", Icons.Default.CloudQueue, "35%"),
            HourlyForecast("6 PM", "29°C", Icons.Default.Grain, "60%"),
            HourlyForecast("9 PM", "26°C", Icons.Default.NightsStay, "20%")
        )
    }

    val weeklyData = remember(locationName) {
        listOf(
            DailyForecast("Tomorrow", "Scattered Showers", "25°C - 31°C", Icons.Default.Grain),
            DailyForecast("Friday", "Partly Cloudy", "24°C - 33°C", Icons.Default.Cloud),
            DailyForecast("Saturday", "Thunderstorms", "23°C - 29°C", Icons.Default.Thunderstorm),
            DailyForecast("Sunday", "Sunny & Clear", "24°C - 34°C", Icons.Default.WbSunny),
            DailyForecast("Monday", "Light Rain", "25°C - 30°C", Icons.Default.WaterDrop)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather & Rain Advisory",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = forestGreen
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F7F4))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Current Weather Card ---
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF1E3A8A), Color(0xFF2563EB), Color(0xFF60A5FA))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Place, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$districtShort District",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = "Offline Mode",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "31°C",
                                    fontSize = 44.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Humid & Partly Cloudy",
                                    fontSize = 14.sp,
                                    color = Color(0xFFDBEAFE),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.CloudQueue,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(68.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Metric pills: Humidity, Wind, Rain
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherMetricPill(icon = Icons.Default.WaterDrop, label = "Humidity", value = "72%")
                            WeatherMetricPill(icon = Icons.Default.Air, label = "Wind", value = "14 km/h")
                            WeatherMetricPill(icon = Icons.Default.Grain, label = "Rain Prob", value = "40%")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Farmer Agricultural Advisory Alert ---
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFFFFFBEB),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(sunnyAmber.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Agricultural Advisory for $districtShort",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Scattered rain expected by evening. Postpone chemical spraying and nitrogen fertilizer application to prevent nutrient runoff.",
                            fontSize = 12.sp,
                            color = Color(0xFF78350F),
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Hourly Breakdown ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hourly Breakdown",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(hourlyData) { hour ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 2.dp,
                        modifier = Modifier.width(78.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = hour.time, fontSize = 12.sp, color = Color(0xFF64748B))
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(hour.icon, contentDescription = null, tint = skyBlue, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = hour.temp, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = hour.rainProb, fontSize = 10.sp, color = skyBlue, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 5-Day Outlook ---
            Text(
                text = "5-Day Farm Forecast",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)) {
                    weeklyData.forEachIndexed { index, forecast ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(forecast.icon, contentDescription = null, tint = skyBlue, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = forecast.day, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                                Text(text = forecast.condition, fontSize = 11.sp, color = Color(0xFF64748B))
                            }
                            Text(text = forecast.tempRange, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        }
                        if (index < weeklyData.size - 1) {
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherMetricPill(icon: ImageVector, label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(text = label, fontSize = 9.sp, color = Color(0xFFDBEAFE))
                Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}