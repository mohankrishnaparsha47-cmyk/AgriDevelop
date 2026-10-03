package com.example.agridevelop

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AgriVideo(
    val id: String,
    val videoKey: String,
    val titleEn: String,
    val titleTe: String,
    val category: String,
    val duration: String,
    val channelName: String
)

object VideosRepository {
    val videosList = listOf(
        AgriVideo(
            id = "vid_1",
            videoKey = "drip_irrigation",
            titleEn = "Modern Drip Irrigation Setup & Subsidy Guide",
            titleTe = "డ్రిప్ ఇరిగేషన్ ఏర్పాటు మరియు ప్రభుత్వ రాయితీలు",
            category = "Irrigation",
            duration = "14:20",
            channelName = "Telangana Agri Extension"
        ),
        AgriVideo(
            id = "vid_2",
            videoKey = "paddy_dsr",
            titleEn = "Paddy Direct Seeding Technique (DSR Method)",
            titleTe = "వరిలో వెదజల్లే పద్ధతి (DSR) - తక్కువ ఖర్చుతో అధిక దిగుబడి",
            category = "Crops",
            duration = "18:45",
            channelName = "Rythu Nestham"
        ),
        AgriVideo(
            id = "vid_3",
            videoKey = "cotton_pest",
            titleEn = "Pink Bollworm Control in Cotton Fields",
            titleTe = "పత్తిలో గులాబీ రంగు పురుగు నివారణ పద్ధతులు",
            category = "Pest Control",
            duration = "11:15",
            channelName = "PJTSAU Advisory"
        ),
        AgriVideo(
            id = "vid_4",
            videoKey = "jeevamrutham",
            titleEn = "Organic Jeevamrutham & Natural Fertilizers",
            titleTe = "జీవామృతం తయారీ మరియు సహజ ఎరువుల వాడకం",
            category = "Organic",
            duration = "16:30",
            channelName = "Natural Farming Mission"
        ),
        AgriVideo(
            id = "vid_5",
            videoKey = "chilli_farming",
            titleEn = "Chilli Crop High-Yield Tips & Virus Protection",
            titleTe = "మిర్చి సాగులో తామర పురుగు & నల్ల తామర పురుగు నివారణ",
            category = "Pest Control",
            duration = "13:50",
            channelName = "Kisan Advice Telugu"
        ),
        AgriVideo(
            id = "vid_6",
            videoKey = "mini_tractors",
            titleEn = "Farm Mechanization: Power Tillers & Mini Tractors",
            titleTe = "వ్యవసాయంలో చిన్న ట్రాక్టర్లు & పవర్ టిల్లర్ల వినియోగం",
            category = "Machinery",
            duration = "09:40",
            channelName = "Agri Machinery Review"
        )
    )
}

fun playYouTubeVideo(context: Context, videoKey: String) {
    val searchQuery = when (videoKey) {
        "drip_irrigation" -> "telangana drip irrigation subsidy farming telugu"
        "paddy_dsr" -> "direct seeded rice paddy DSR method telugu farming"
        "cotton_pest" -> "cotton pink bollworm management telugu PJTSAU"
        "jeevamrutham" -> "how to prepare jeevamrutham natural farming telugu"
        "chilli_farming" -> "chilli thrips black thrips control telugu"
        "mini_tractors" -> "mini tractor power tiller subsidy telangana farming"
        else -> "telangana agriculture modern farming"
    }

    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.youtube.com/results?search_query=" + Uri.encode(searchQuery))
    ).apply {
        setPackage("com.google.android.youtube")
    }

    try {
        context.startActivity(intent)
    } catch (_: Exception) {
        // Fallback to browser if the official YouTube app is not installed
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.youtube.com/results?search_query=" + Uri.encode(searchQuery))
        )
        context.startActivity(webIntent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosScreen(
    onBack: () -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val forestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)
    val context = LocalContext.current

    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Crops", "Pest Control", "Irrigation", "Organic", "Machinery")

    val filteredVideos = if (selectedCategory == "All") {
        VideosRepository.videosList
    } else {
        VideosRepository.videosList.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Farming Tutorials (సాగు వీడియోలు)",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkForest)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBg)
                .padding(padding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) darkForest else Color.White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) darkForest else Color(0xFFDDE7DF)
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(14.dp))
                    ) {
                        Text(
                            text = cat,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF475569),
                            modifier = Modifier
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredVideos) { video ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { playYouTubeVideo(context, video.videoKey) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(Color(0xFF1E3A2B), Color(0xFF0F5A2C), Color(0xFF2E7D32))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    modifier = Modifier.size(52.dp),
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.9f)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play Video",
                                            tint = forestGreen,
                                            modifier = Modifier.size(34.dp)
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(10.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.Black.copy(alpha = 0.75f)
                                ) {
                                    Text(
                                        text = video.duration,
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = video.titleEn,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = video.titleTe,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = forestGreen
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = video.channelName,
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.PlayCircleFilled,
                                            contentDescription = null,
                                            tint = Color(0xFFDC2626),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Watch Tutorial",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFDC2626)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}