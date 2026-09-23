package com.example.agridevelop

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FarmingTutorial(
    val title: String,
    val instructor: String,
    val duration: String,
    val category: String,
    val summary: String,
    val videoUrl: String,
    val accentColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)

    val tutorials = remember {
        listOf(
            FarmingTutorial(
                title = "Drip Irrigation Setup & Daily Maintenance",
                instructor = "PJTSAU Agronomist Desk",
                duration = "12 min",
                category = "Water Management",
                summary = "Master lateral line flushing, venturi injector acid cleaning, and emission uniformity for chili and cotton crops.",
                videoUrl = "https://www.youtube.com/results?search_query=drip+irrigation+maintenance+telangana",
                accentColor = Color(0xFF0288D1)
            ),
            FarmingTutorial(
                title = "Neem Oil & Natural Jeevamrutha Preparation",
                instructor = "Organic Farmers Association",
                duration = "15 min",
                category = "Organic Pest Control",
                summary = "Step-by-step fermentation of cow dung, urine, jaggery, and pulse flour for soil microbial boost and natural aphid deterrence.",
                videoUrl = "https://www.youtube.com/results?search_query=jeevamrutham+preparation+telugu",
                accentColor = Color(0xFF2E7D32)
            ),
            FarmingTutorial(
                title = "Paddy Direct Seeding (DSR) Technique",
                instructor = "Krishi Vigyan Kendra (KVK)",
                duration = "18 min",
                category = "Paddy Cultivation",
                summary = "Reduce labor and nursery costs by 30% using drum seeders, seed priming methods, and early pre-emergence weed control.",
                videoUrl = "https://www.youtube.com/results?search_query=direct+seeded+rice+dsr+farming",
                accentColor = Color(0xFFE65100)
            ),
            FarmingTutorial(
                title = "NPK Fertilizer Calculation & Micro-Nutrients",
                instructor = "Soil Health Advisory Unit",
                duration = "10 min",
                category = "Nutrient Management",
                summary = "Avoid over-applying Urea; calculate exact Zinc Sulphate and Boron split applications based on soil lab test values.",
                videoUrl = "https://www.youtube.com/results?search_query=npk+fertilizer+calculation+farmers",
                accentColor = Color(0xFFC2185B)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Farming Video Guides", fontWeight = FontWeight.Bold, color = Color.White) },
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
            items(tutorials) { video ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.videoUrl))
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = video.accentColor.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = video.category,
                                    color = video.accentColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(video.duration, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(video.accentColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(video.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1E293B))
                                Text(video.instructor, fontSize = 12.sp, color = Color(0xFF64748B))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(video.summary, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 17.sp)

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("▶ Tap to Open Video Demonstration", color = video.accentColor, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}