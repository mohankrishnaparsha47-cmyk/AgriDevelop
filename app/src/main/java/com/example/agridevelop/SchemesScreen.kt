package com.example.agridevelop

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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
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
import androidx.browser.customtabs.CustomTabsIntent

data class AgriScheme(
    val id: String,
    val titleEn: String,
    val titleTe: String,
    val category: String,
    val benefitAmount: String,
    val eligibility: String,
    val documentsNeeded: List<String>,
    val portalUrl: String
)

object SchemesRepository {
    val schemesList = listOf(
        AgriScheme(
            id = "rythu_bharosa",
            titleEn = "Rythu Bharosa / Investment Support",
            titleTe = "రైతు భరోసా పథకం",
            category = "Financial Support",
            benefitAmount = "₹15,000 / Acre / Year",
            eligibility = "All pattadar farmers and verified tenant farmers cultivating land in Telangana.",
            documentsNeeded = listOf("Pattadar Passbook / Dharani Record", "Aadhaar Card", "Active Bank Account linked with NPCI"),
            portalUrl = "https://dharani.telangana.gov.in"
        ),
        AgriScheme(
            id = "pm_kisan",
            titleEn = "PM-Kisan Samman Nidhi",
            titleTe = "పీఎం కిసాన్ సమ్మాన్ నిధి",
            category = "Financial Support",
            benefitAmount = "₹6,000 / Year (3 Installments)",
            eligibility = "Small and marginal farmer families holding cultivable land in their name.",
            documentsNeeded = listOf("Aadhaar Card with e-KYC", "Land Revenue Records (ROR)", "Bank Account (DBT Enabled)"),
            portalUrl = "https://pmkisan.gov.in"
        ),
        AgriScheme(
            id = "drip_subsidy",
            titleEn = "Telangana Micro Irrigation Project (TSMIP)",
            titleTe = "సూక్ష్మ సేద్యం రాయితీ (డ్రిప్ & స్ప్రింక్లర్)",
            category = "Subsidy & Equipment",
            benefitAmount = "Up to 90% Subsidy for SC/ST, 80% for BC",
            eligibility = "Farmers possessing minimum 0.5 acre with functional borewell / water source.",
            documentsNeeded = listOf("Borewell Water & Electricity Certificate", "Land Records / Dharani 1B", "Soil Test Certificate"),
            portalUrl = "https://horticulture.telangana.gov.in"
        ),
        AgriScheme(
            id = "pmfby",
            titleEn = "Pradhan Mantri Fasal Bima Yojana (Crop Insurance)",
            titleTe = "ప్రధాన మంత్రి ఫసల్ బీమా యోజన",
            category = "Insurance",
            benefitAmount = "Full Sum Insured for Drought/Pest/Flood Damage",
            eligibility = "All farmers growing notified crops (Cotton, Paddy, Red Gram, Chilli) in notified areas.",
            documentsNeeded = listOf("Sowing Certificate issued by VRO/AEO", "Pattadar Passbook", "Aadhaar Card"),
            portalUrl = "https://pmfby.gov.in"
        ),
        AgriScheme(
            id = "farm_mechanization",
            titleEn = "Farm Mechanization Scheme (Tractor & Implements)",
            titleTe = "వ్యవసాయ యాంత్రీకరణ పథకం",
            category = "Subsidy & Equipment",
            benefitAmount = "50% Subsidy on Rotavators, Harvesters & Sprayers",
            eligibility = "Individual farmers or Farmer Producer Organizations (FPOs) registered in Telangana.",
            documentsNeeded = listOf("Aadhaar Card", "Land Title Passbook", "Quotation from Authorized Dealer"),
            portalUrl = "https://agri.telangana.gov.in"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemesScreen(
    onBack: () -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val forestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)
    val context = LocalContext.current

    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Financial Support", "Subsidy & Equipment", "Insurance")

    val filteredSchemes = if (selectedCategory == "All") {
        SchemesRepository.schemesList
    } else {
        SchemesRepository.schemesList.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Government Schemes (రైతు పథకాలు)",
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
            // Category Filter Pills
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

            // Schemes Cards List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredSchemes) { scheme ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Header Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = scheme.titleEn,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                    Text(
                                        text = scheme.titleTe,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = forestGreen
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7)
                                ) {
                                    Text(
                                        text = scheme.benefitAmount,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF15803D),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(10.dp))

                            // Eligibility
                            Text(
                                text = "Eligibility / అర్హత:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF475569)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = scheme.eligibility,
                                fontSize = 12.sp,
                                color = Color(0xFF1E293B),
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Required Documents Checklist
                            Text(
                                text = "Required Documents (కావలసిన ధ్రువీకరణ పత్రాలు):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF475569)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            scheme.documentsNeeded.forEach { doc ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 1.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF16A34A),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = doc, fontSize = 12.sp, color = Color(0xFF334155))
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Official Portal Button
                            Button(
                                onClick = {
                                    val customTabsIntent = CustomTabsIntent.Builder()
                                        .setShowTitle(true)
                                        .build()
                                    customTabsIntent.launchUrl(context, Uri.parse(scheme.portalUrl))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
                            ) {
                                Text("Apply on Official Portal", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}