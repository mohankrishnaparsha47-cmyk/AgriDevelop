package com.example.agridevelop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class SoilTypeData(
    val id: String,
    val name: String,
    val localName: String,
    val phRange: String,
    val texture: String,
    val waterHolding: String,
    val topCrops: List<String>,
    val fertilizerRecommendation: String,
    val themeColor: Color,
    val localImageRes: Int? = null,
    val webImageUrl: String? = null
)

object SoilCatalog {
    val allSoils = listOf(
        SoilTypeData(
            id = "black_soil",
            name = "Black Soil (Regur / Clay)",
            localName = "నల్లరేగడి నేలలు",
            phRange = "7.2 - 8.5 (Slightly Alkaline)",
            texture = "Deep clayey, develops wide cracks in summer",
            waterHolding = "Very High (Retains moisture for 8-10 days)",
            topCrops = listOf(
                "Cotton (పత్తి)",
                "Soybean (సోయాబీన్)",
                "Chilli (మిరప)",
                "Bengal Gram (శనగ)",
                "Paddy (వరి)",
                "Sunflower (పొద్దుతిరుగుడు)"
            ),
            fertilizerRecommendation = "Rich in Calcium, Magnesium, and Potassium. Moderately low in Nitrogen and Zinc. Apply DAP/SSP at base and supplement 10-15 kg Zinc Sulphate per acre.",
            themeColor = Color(0xFF37474F),
            localImageRes = R.drawable.black_soil
        ),
        SoilTypeData(
            id = "red_soil",
            name = "Red Sandy Soil (Chalaka)",
            localName = "ఎర్ర నేలలు / చలక నేలలు",
            phRange = "6.0 - 7.2 (Neutral to Slightly Acidic)",
            texture = "Porous, coarse sandy to gravelly loam",
            waterHolding = "Moderate to Low (Quick draining)",
            topCrops = listOf(
                "Groundnut (వేరుశనగ)",
                "Maize (మొక్కజొన్న)",
                "Red Gram / Toor (కంది)",
                "Castor (ఆముదం)",
                "Millets / Jowar (జొన్నలు)",
                "Tomato (టమోటా)"
            ),
            fertilizerRecommendation = "Deficient in Nitrogen, Phosphorus, and Humus. Apply 4-5 tonnes Farm Yard Manure (FYM) per acre, Gypsum for groundnut pods, and split Nitrogen doses.",
            themeColor = Color(0xFFD84315),
            localImageRes = R.drawable.red_soil
        ),
        SoilTypeData(
            id = "alluvial_soil",
            name = "Alluvial & Loamy Soil",
            localName = "ఒండ్రు నేలలు / మెత్తని మట్టి",
            phRange = "6.5 - 7.5 (Optimal Neutral)",
            texture = "Balanced silt, clay, and fine sand",
            waterHolding = "High & Well aerated",
            topCrops = listOf(
                "Paddy (వరి)",
                "Sugarcane (చెరకు)",
                "Banana (అరటి)",
                "Turmeric (పసుపు)",
                "Wheat (గోధుమ)",
                "Vegetables (కూరగాయలు)"
            ),
            fertilizerRecommendation = "Naturally fertile with balanced Potash. Recommended NPK regimen (120:60:40 kg/ha). Use green manuring (Dhaincha/Sunnhemp) before transplanting.",
            themeColor = Color(0xFF5D4037),
            webImageUrl = "https://images.unsplash.com/photo-1625246333195-78d9c38ad449?q=80&w=400&auto=format&fit=crop"
        ),
        SoilTypeData(
            id = "laterite_soil",
            name = "Laterite & Gravelly Soil",
            localName = "లేటరైట్ / ఎరుపు గరప నేలలు",
            phRange = "5.0 - 6.0 (Acidic)",
            texture = "Coarse gravelly with iron and aluminum oxides",
            waterHolding = "Low (Highly permeable, quick leaching)",
            topCrops = listOf(
                "Cashew (జీడిమామిడి)",
                "Mango (మామిడి)",
                "Turmeric (పసుపు)",
                "Ginger (అల్లం)",
                "Coffee (కాఫీ)",
                "Tapioca (కర్రపెండలం)"
            ),
            fertilizerRecommendation = "High acidity causes phosphorus fixation. Apply Agricultural Lime @ 200-250 kg/acre and Rock Phosphate with organic compost.",
            themeColor = Color(0xFFBF360C),
            localImageRes = R.drawable.laterate_soil
        ),
        SoilTypeData(
            id = "saline_soil",
            name = "Saline & Alkaline Soil (Chowdu)",
            localName = "చౌడు నేలలు / క్షార నేలలు",
            phRange = "8.5 - 9.5 (Highly Alkaline)",
            texture = "Dense, crusted white saline surface with poor drainage",
            waterHolding = "Poor infiltration, water pools easily",
            topCrops = listOf(
                "Barley (బార్లీ)",
                "Dhaincha (జీలుగ)",
                "Cotton (పత్తి - Salt Tolerant varieties)",
                "Bermuda Grass (గడ్డి జాతులు)",
                "Mustard (ఆవాలు)"
            ),
            fertilizerRecommendation = "Requires soil reclamation. Apply Gypsum @ 1.5 - 2 tonnes/acre followed by flushing with clean water. Avoid basic fertilizers like Calcium Nitrate.",
            themeColor = Color(0xFF6B7280),
            localImageRes = R.drawable.alkaline_soil
        ),
        SoilTypeData(
            id = "sandy_soil",
            name = "Sandy & Riverbed Soil",
            localName = "ఇసుక నేలలు / రేగడి ఇసుక",
            phRange = "6.5 - 7.5 (Neutral)",
            texture = "Coarse, 85%+ sand particles with rapid water loss",
            waterHolding = "Very Low (Requires daily or alternate day watering)",
            topCrops = listOf(
                "Watermelon (పుచ్చకాయ)",
                "Muskmelon (కర్బూజ)",
                "Cucumber (దోసకాయ)",
                "Groundnut (వేరుశనగ)",
                "Carrot & Radish (క్యారెట్, ముల్లంగి)"
            ),
            fertilizerRecommendation = "Severe fertilizer leaching occurs. Apply slow-release fertilizers or fertigation via Drip irrigation. Incorporate coir pith or vermicompost to increase water retention.",
            themeColor = Color(0xFF8B5CF6),
            webImageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=400&auto=format&fit=crop"
        ),
        SoilTypeData(
            id = "peaty_soil",
            name = "Peaty & Marshy Soil",
            localName = "ఊబి నేలలు / బురద నేలలు",
            phRange = "3.8 - 5.2 (Strongly Acidic)",
            texture = "Heavy, black, spongy organic residue with excessive moisture",
            waterHolding = "Extremely High (Waterlogged)",
            topCrops = listOf(
                "Deepwater Paddy (వరి)",
                "Jute (జనపనార)",
                "Colocasia / Taro (చామదుంప)",
                "Lotus & Aquatic Greens"
            ),
            fertilizerRecommendation = "Abundant in organic matter but deficient in Potash and Phosphate. Requires drainage trenches, lime application, and Potassium supplementation.",
            themeColor = Color(0xFF047857),
            localImageRes = R.drawable.marshy_soil
        ),
        SoilTypeData(
            id = "forest_soil",
            name = "Forest & Hill Loam Soil",
            localName = "అటవీ నేలలు / పర్వత నేలలు",
            phRange = "5.5 - 6.5 (Slightly Acidic)",
            texture = "Humus-rich, loamy, silty mountain floor",
            waterHolding = "Moderate to High",
            topCrops = listOf(
                "Tea (టీ)",
                "Cardamom (యాలకులు)",
                "Black Pepper (మిరియాలు)",
                "Orchard Citrus (నిమ్మ / నారింజ)",
                "Ginger (అల్లం)"
            ),
            fertilizerRecommendation = "Rich in natural leaf humus and nitrogen. Needs balanced micronutrients (Zinc, Boron) and contour bunding to prevent topsoil erosion on slopes.",
            themeColor = Color(0xFF15803D),
            webImageUrl = "https://images.unsplash.com/photo-1448375240586-882707db888b?q=80&w=400&auto=format&fit=crop"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilListScreen(
    onBack: () -> Unit,
    onSoilClick: (soilId: String) -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)
    val context = LocalContext.current

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
                        .clickable { onSoilClick(soil.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (soil.localImageRes != null) {
                                Image(
                                    painter = painterResource(id = soil.localImageRes),
                                    contentDescription = soil.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(62.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                )
                            } else if (soil.webImageUrl != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(soil.webImageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = soil.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(62.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = soil.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = soil.localName,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "pH: ${soil.phRange}",
                                    fontSize = 11.sp,
                                    color = soil.themeColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open Details",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}