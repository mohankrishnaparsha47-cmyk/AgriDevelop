package com.example.agridevelop

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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

data class AgriScheme(
    val name: String,
    val authority: String,
    val aidAmount: String,
    val overview: String,
    val eligibility: List<String>,
    val documentsNeeded: List<String>,
    val howToApply: String,
    val officialUrl: String,
    val badgeColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemesScreen(onBack: () -> Unit) {
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)

    val schemesList = remember {
        listOf(
            AgriScheme(
                name = "Rythu Bharosa (Investment Support)",
                authority = "Government of Telangana",
                aidAmount = "₹12,000 - ₹15,000 / Acre / Year",
                overview = "Flagship seasonal investment assistance providing direct financial support for seeds, fertilizers, and field preparation for Kharif and Rabi cycles.",
                eligibility = listOf(
                    "Permanent resident cultivators of Telangana",
                    "Pattadar Passbook holders registered on Bhu Bharati / Dharani",
                    "Aadhaar-linked active bank account (DBT mandatory)"
                ),
                documentsNeeded = listOf("Pattadar Passbook", "Aadhaar Card", "Active Bank Passbook"),
                howToApply = "Verify account and land records on the official Telangana Government Rythu Bharosa / Agriculture portal.",
                officialUrl = "https://rythubharosa.telangana.gov.in/",
                badgeColor = Color(0xFF2E7D32)
            ),
            AgriScheme(
                name = "Rythu Bima (Farmer Life Insurance)",
                authority = "Government of Telangana & LIC",
                aidAmount = "₹5,00,000 Group Insurance Cover",
                overview = "100% government-subsidized life insurance providing financial relief to the nominated family members within 10 days of a farmer's untimely demise.",
                eligibility = listOf(
                    "Farmer age must be between 18 and 59 years",
                    "Must own land registered in Dharani/Pattadar revenue records",
                    "Nominee declaration form submitted to Agriculture Extension Officer"
                ),
                documentsNeeded = listOf("Pattadar Passbook", "Aadhaar Card of Farmer & Nominee", "Bank Account Details"),
                howToApply = "Managed directly via Agriculture Extension Officers. View claim forms and guidelines on the department portal.",
                officialUrl = "http://rythubima.telangana.gov.in/",
                badgeColor = Color(0xFF0288D1)
            ),
            AgriScheme(
                name = "PM-Kisan Samman Nidhi",
                authority = "Central Government (Govt of India)",
                aidAmount = "₹6,000 / Year (3 x ₹2,000 Installments)",
                overview = "Direct income support credited in three equal quarterly installments into farmer bank accounts to assist with operational farming input costs.",
                eligibility = listOf(
                    "All landholding farmer families across India",
                    "Mandatory Aadhaar e-KYC on PM-Kisan portal",
                    "Institutional landholders and income tax payees excluded"
                ),
                documentsNeeded = listOf("Land Record (Pahani/1B)", "Aadhaar Card", "NPCI-Linked Bank Account"),
                howToApply = "Check beneficiary status, perform e-KYC, or apply directly on the PM-Kisan portal.",
                officialUrl = "https://pmkisan.gov.in/",
                badgeColor = Color(0xFFE65100)
            ),
            AgriScheme(
                name = "Farm Mechanization & Micro-Irrigation (Drip)",
                authority = "Telangana State Micro Irrigation Project (TSMIP)",
                aidAmount = "Up to 50% - 80% Subsidy",
                overview = "Assistance for micro-irrigation (Drip/Sprinkler systems), power tillers, rotavators, and sprayers to maximize yield efficiency.",
                eligibility = listOf(
                    "Small and marginal farmers receive higher subsidy tier (up to 80%)",
                    "Must own operational borewell or verified irrigation source",
                    "Prior benefit receipt limit of 3 to 5 years apply per machine"
                ),
                documentsNeeded = listOf("Pattadar Passbook", "Caste Certificate (if SC/ST/BC)", "Borewell Water Availability Certificate"),
                howToApply = "Submit application for subsidy verification on the Telangana Micro Irrigation portal.",
                officialUrl = "http://horticulturedept.telangana.gov.in/",
                badgeColor = Color(0xFFC2185B)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Government Schemes", fontWeight = FontWeight.Bold, color = Color.White) },
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
            items(schemesList) { scheme ->
                SchemeExpandableCard(scheme)
            }
        }
    }
}

@Composable
fun SchemeExpandableCard(scheme: AgriScheme) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    fun openSchemePortal() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scheme.officialUrl))
            context.startActivity(intent)
        } catch (_: Exception) {
            // Safeguard against missing browser applications
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(scheme.badgeColor.copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = scheme.badgeColor)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(scheme.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1E293B))
                        Text(scheme.authority, fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = scheme.badgeColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = scheme.aidAmount,
                    color = scheme.badgeColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(scheme.overview, fontSize = 13.sp, color = Color(0xFF475569), lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // Direct Redirect Button on the Card
            Button(
                onClick = { openSchemePortal() },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = scheme.badgeColor)
            ) {
                Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Official Scheme Portal", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            // Expandable details (Requirements & Documents)
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Eligibility Criteria:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
                    scheme.eligibility.forEach { item ->
                        Text("• $item", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 2.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Required Documents:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
                    scheme.documentsNeeded.forEach { doc ->
                        Text("• $doc", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 2.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("How to Apply:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = scheme.badgeColor)
                    Text(scheme.howToApply, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                }
            }
        }
    }
}