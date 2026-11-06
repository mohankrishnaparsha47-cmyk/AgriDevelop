package com.example.agridevelop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilDetailScreen(
    soilId: String = "black_soil",
    onBack: () -> Unit
) {
    val forestGreen = Color(0xFF0F5A2C)
    val soil = SoilDataRepository.soils.find { it.id == soilId } ?: SoilDataRepository.soils.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(soil.nameEn, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = forestGreen)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F7F4))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = soil.nameTe, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = forestGreen)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = soil.descriptionEn, fontSize = 15.sp, color = Color(0xFF475569))
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoBadge(icon = Icons.Default.Thermostat, label = "Temp", value = soil.tempRange)
                            InfoBadge(icon = Icons.Default.Science, label = "pH", value = soil.phRange)
                            InfoBadge(icon = Icons.Default.WaterDrop, label = "Moisture", value = soil.moistureHold)
                        }
                    }
                }
            }

            item {
                Text(text = "Recommended Crops", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            }
            items(soil.recommendedCrops) { crop ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Eco, contentDescription = null, tint = forestGreen)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = crop, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                    }
                }
            }

            item {
                Text(text = "Farming Tips", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            }
            items(soil.tips) { tip ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Text(text = "• $tip", modifier = Modifier.padding(16.dp), fontSize = 14.sp, color = Color(0xFF1B5E20))
                }
            }

            item {
                Text(text = "Traditional & Modern Methods", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Traditional: ${soil.traditionalMethods.joinToString(", ")}", fontSize = 14.sp, color = Color(0xFF475569))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Modern: ${soil.modernMethods.joinToString(", ")}", fontSize = 14.sp, color = Color(0xFF475569))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Suitable Fertilizers: ${soil.suitableFertilizers.joinToString(", ")}", fontSize = 14.sp, color = Color(0xFF475569))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF0F5A2C), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
    }
}
