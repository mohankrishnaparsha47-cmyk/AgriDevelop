package com.example.agridevelop
import androidx.compose.runtime.Composable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.Modifier

@Composable
fun SoilCatalog() {

    val soilTypes = listOf(
        "Black Soil" to "Suitable for cotton, soybean and wheat",
        "Red Soil" to "Suitable for groundnut, millets and pulses",
        "Alluvial Soil" to "Suitable for rice, wheat and sugarcane",
        "Laterite Soil" to "Suitable for tea, coffee and cashew",
        "Sandy Soil" to "Suitable for watermelon and groundnut",
        "Clay Soil" to "Suitable for rice and other water-loving crops"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7EF))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Soil Catalog",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF245C2A)
        )

        Text(
            text = "Explore different types of soil",
            fontSize = 15.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        soilTypes.forEach { soil ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(modifier = Modifier.padding(18.dp)) {

                    Text(
                        text = soil.first,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF28652D)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = soil.second,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}