package com.example.agridevelop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SoilTypeData(
    val name: String,
    val localName: String,
    val phRange: String,
    val texture: String,
    val waterHolding: String,
    val topCrops: List<String>,
    val fertilizerRecommendation: String,
    val themeColor: Color
)

object SoilCatalog {
    val allSoils = listOf(
        SoilTypeData(
            name = "Black Soil (Regur)",
            localName = "నల్లరేగడి నేలలు",
            phRange = "7.2 - 8.5 (Slightly Alkaline)",
            texture = "Clayey, deep cracking when dry",
            waterHolding = "Very High (Retains moisture long)",
            topCrops = listOf("Cotton (పత్తి)", "Soybean (సోయాబీన్)", "Chili (మిరప)", "Bengal Gram (శనగ)", "Paddy (వరి)"),
            fertilizerRecommendation = "Rich in Calcium and Magnesium, but deficient in Nitrogen and Phosphorus. Apply Single Super Phosphate (SSP) and Zinc Sulphate.",
            themeColor = Color(0xFF37474F)
        ),
        SoilTypeData(
            name = "Red Soil (Chalaka)",
            localName = "ఎర్ర నేలలు / చలక నేలలు",
            phRange = "6.0 - 7.2 (Neutral to Slightly Acidic)",
            texture = "Porous, sandy to gravelly loam",
            waterHolding = "Moderate to Low (Quick draining)",
            topCrops = listOf("Groundnut (వేరుశనగ)", "Maize (మొక్కజొన్న)", "Red Gram (కంది)", "Castor (ఆముదం)", "Millets (చిరుధాన్యాలు)"),
            fertilizerRecommendation = "Low in organic carbon and Nitrogen. Requires regular Farm Yard Manure (FYM) addition and split Urea applications to prevent leaching.",
            themeColor = Color(0xFFD84315)
        ),
        SoilTypeData(
            name = "Alluvial & Loamy Soil",
            localName = "ఒండ్రు నేలలు / మెత్తని నేలలు",
            phRange = "6.5 - 7.5 (Optimal Neutral)",
            texture = "Balanced silt, clay, and sand mix",
            waterHolding = "High & Well aerated",
            topCrops = listOf("Paddy (వరి)", "Sugarcane (చెరకు)", "Banana (అరటి)", "Vegetables (కూరగాయలు)", "Wheat"),
            fertilizerRecommendation = "Naturally fertile with rich potash content. Balanced NPK 120:60:40 kg/ha regimen advised for commercial grain crops.",
            themeColor = Color(0xFF795548)
        ),
        SoilTypeData(
            name = "Laterite Soil",
            localName = "లేటరైట్ / ఎరుపు గరప నేలలు",
            phRange = "5.0 - 6.0 (Acidic)",
            texture = "Coarse gravelly with iron oxides",
            waterHolding = "Low (Highly permeable)",
            topCrops = listOf("Cashew (జీడిమామిడి)", "Mango (మామిడి)", "Turmeric (పసుపు)", "Ginger (అల్లం)"),
            fertilizerRecommendation = "Acidic profile causes phosphorus fixation. Apply agricultural lime @ 200 kg/acre and rock phosphate before sowing.",
            themeColor = Color(0xFFBF360C)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilListScreen(
    onBack: () -> Unit,
    onSoilClick: (soilName: String) -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Soil Types & Crop Fit", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkForest)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(pageBg)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(SoilCatalog.allSoils) { soil ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSoilClick(soil.name) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(soil.themeColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Landscape, contentDescription = null, tint = soil.themeColor)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(soil.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1E293B))
                                Text(soil.localName, fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("pH: ${soil.phRange}", fontSize = 11.sp, color = soil.themeColor, fontWeight = FontWeight.SemiBold)
                            }
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                    }
                }
            }
        }
    }
}