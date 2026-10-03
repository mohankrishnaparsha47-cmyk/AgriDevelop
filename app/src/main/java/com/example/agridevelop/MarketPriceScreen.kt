package com.example.agridevelop

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MandiCommodity(
    val id: String,
    val nameEn: String,
    val nameTe: String,
    val category: String,
    val mandiYard: String,
    val minPrice: Int,
    val modalPrice: Int,
    val maxPrice: Int,
    val priceChange: String,
    val isUp: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPriceScreen(
    locationName: String = "Nalgonda, Telangana",
    onBack: () -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)
    val districtShort = locationName.substringBefore(",").trim()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Cereals", "Commercial", "Pulses", "Spices", "Oilseeds")

    // Dynamic market dataset tailored per district
    val commodities = remember(locationName) {
        listOf(
            MandiCommodity(
                id = "cotton",
                nameEn = "Cotton (Medium Staple)",
                nameTe = "పత్తి",
                category = "Commercial",
                mandiYard = "$districtShort Agriculture Market Yard",
                minPrice = 6950,
                modalPrice = 7380,
                maxPrice = 7650,
                priceChange = "+₹120",
                isUp = true
            ),
            MandiCommodity(
                id = "paddy_fine",
                nameEn = "Paddy (Fine / Sona Masoori)",
                nameTe = "సన్న వరి (సోనా మసూరి)",
                category = "Cereals",
                mandiYard = "$districtShort Regulated Mandi",
                minPrice = 2240,
                modalPrice = 2360,
                maxPrice = 2480,
                priceChange = "+₹40",
                isUp = true
            ),
            MandiCommodity(
                id = "paddy_common",
                nameEn = "Paddy (Common / Doddu)",
                nameTe = "వరి (దొడ్డు రకం)",
                category = "Cereals",
                mandiYard = "$districtShort Agriculture Market",
                minPrice = 2160,
                modalPrice = 2210,
                maxPrice = 2280,
                priceChange = "+₹15",
                isUp = true
            ),
            MandiCommodity(
                id = "chilli",
                nameEn = "Dry Red Chilli (Teja / Fatki)",
                nameTe = "ఎండు మిర్చి (తేజ)",
                category = "Spices",
                mandiYard = if (districtShort.contains("Khammam", true) || districtShort.contains("Warangal", true))
                    "$districtShort Mirchi Yard" else "$districtShort APMC Mandi",
                minPrice = 17200,
                modalPrice = 18800,
                maxPrice = 20600,
                priceChange = "-₹280",
                isUp = false
            ),
            MandiCommodity(
                id = "red_gram",
                nameEn = "Red Gram / Toor (Kandi)",
                nameTe = "కంది",
                category = "Pulses",
                mandiYard = "$districtShort Main Committee",
                minPrice = 7900,
                modalPrice = 8350,
                maxPrice = 8700,
                priceChange = "+₹95",
                isUp = true
            ),
            MandiCommodity(
                id = "maize",
                nameEn = "Maize (Yellow Corn)",
                nameTe = "మొక్కజొన్న",
                category = "Cereals",
                mandiYard = "$districtShort Yard 2",
                minPrice = 1960,
                modalPrice = 2100,
                maxPrice = 2230,
                priceChange = "-₹30",
                isUp = false
            ),
            MandiCommodity(
                id = "soybean",
                nameEn = "Soybean (Yellow)",
                nameTe = "సోయాబీన్",
                category = "Oilseeds",
                mandiYard = "$districtShort APMC Market",
                minPrice = 4320,
                modalPrice = 4600,
                maxPrice = 4780,
                priceChange = "+₹65",
                isUp = true
            ),
            MandiCommodity(
                id = "groundnut",
                nameEn = "Groundnut (Pods)",
                nameTe = "వేరుశనగ కాయలు",
                category = "Oilseeds",
                mandiYard = "$districtShort Regulated Mandi",
                minPrice = 6100,
                modalPrice = 6550,
                maxPrice = 6800,
                priceChange = "+₹110",
                isUp = true
            ),
            MandiCommodity(
                id = "bengal_gram",
                nameEn = "Bengal Gram (Chana)",
                nameTe = "శనగలు",
                category = "Pulses",
                mandiYard = "$districtShort Main Yard",
                minPrice = 5200,
                modalPrice = 5480,
                maxPrice = 5650,
                priceChange = "-₹40",
                isUp = false
            )
        )
    }

    val filteredList = commodities.filter { item ->
        val matchesSearch = item.nameEn.contains(searchQuery, ignoreCase = true) ||
                item.nameTe.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCategory
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daily Mandi Rates",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 19.sp
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
            // --- District Banner & Search ---
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
                color = darkForest
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFFC8E6C9), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Market: $districtShort, TS",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "e-NAM Live",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search Input Field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search crop (e.g. Cotton, వరి, పత్తి)...", fontSize = 13.sp, color = Color(0xFFA0AEC0)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    )
                }
            }

            // --- Horizontal Category Filter Pills ---
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
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
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF475569),
                            modifier = Modifier
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            // --- Commodity Price Cards ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.nameEn,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${item.nameTe} • ${item.mandiYard}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (item.isUp) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (item.isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                            contentDescription = null,
                                            tint = if (item.isUp) Color(0xFF16A34A) else Color(0xFFDC2626),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = item.priceChange,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (item.isUp) Color(0xFF16A34A) else Color(0xFFDC2626)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(10.dp))

                            // 3-Column Price breakdown: Min, Modal (Average), Max
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                MandiPriceColumn(label = "Min Price", price = "₹${item.minPrice}", highlight = false)
                                MandiPriceColumn(label = "Modal / Average", price = "₹${item.modalPrice}", highlight = true)
                                MandiPriceColumn(label = "Max Price", price = "₹${item.maxPrice}", highlight = false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MandiPriceColumn(label: String, price: String, highlight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 11.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "$price / Qtl",
            fontSize = if (highlight) 15.sp else 13.sp,
            fontWeight = if (highlight) FontWeight.ExtraBold else FontWeight.SemiBold,
            color = if (highlight) Color(0xFF0F5A2C) else Color(0xFF1E293B)
        )
    }
}