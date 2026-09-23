package com.example.agridevelop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agridevelop.R

@Composable
fun ProfileScreen(
    farmerName: String = "Ramesh Kumar",
    mobileNumber: String = "+91 98765 43210",
    location: String = "Telangana, India",
    onLogoutClick: () -> Unit = {}
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English / తెలుగు") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val primaryGreen = Color(0xFF0F5A2C)
    val emeraldGreen = Color(0xFF1B8A46)
    val sunnyAmber = Color(0xFFF59E0B)
    val vibrantBlue = Color(0xFF2563EB)
    val purpleAccent = Color(0xFF7C3AED)
    val crimsonRed = Color(0xFFDC2626)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            // --- Colorful Gradient Header & Profile Hero ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(primaryGreen, emeraldGreen, Color(0xFF34D399))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 28.dp, start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Farmer Profile",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Avatar with badge border
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.dp, sunnyAmber, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agri_logo),
                            contentDescription = "Profile Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = farmerName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "$mobileNumber • $location",
                        color = Color(0xFFE8F5E9),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // --- Quick Highlights Banner ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-24).dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Land Size",
                    value = "4.5 Acres",
                    badgeColor = sunnyAmber,
                    icon = Icons.Default.Landscape
                )
                ProfileMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Main Crop",
                    value = "Paddy / Cotton",
                    badgeColor = emeraldGreen,
                    icon = Icons.Default.Eco
                )
                ProfileMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Status",
                    value = "Verified",
                    badgeColor = vibrantBlue,
                    icon = Icons.Default.Verified
                )
            }

            // --- Settings Section ---
            SectionTitle(title = "App Settings")
            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Default.NotificationsActive,
                    iconColor = sunnyAmber,
                    title = "Crop & Weather Alerts",
                    subtitle = "Daily rainfall and market price notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                SettingsNavRow(
                    icon = Icons.Default.Translate,
                    iconColor = vibrantBlue,
                    title = "Language",
                    subtitle = selectedLanguage,
                    onClick = {
                        selectedLanguage = if (selectedLanguage.startsWith("English")) "తెలుగు (Telugu)" else "English / తెలుగు"
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- Farm Data & Tools Section ---
            SectionTitle(title = "Farm Records & Preferences")
            SettingsCard {
                SettingsNavRow(
                    icon = Icons.Default.Agriculture,
                    iconColor = emeraldGreen,
                    title = "My Crops & Soil Details",
                    subtitle = "Soil test reports, past season yields",
                    onClick = { }
                )
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                SettingsNavRow(
                    icon = Icons.Default.Storefront,
                    iconColor = sunnyAmber,
                    title = "Saved Mandis / Markets",
                    subtitle = "Favorite local grain and vegetable yards",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- About & Support Section ---
            SectionTitle(title = "About & Support")
            SettingsCard {
                SettingsNavRow(
                    icon = Icons.AutoMirrored.Filled.Help,
                    iconColor = purpleAccent,
                    title = "Kisan Helpdesk & Toll-Free",
                    subtitle = "Speak to local agricultural university officers",
                    onClick = { }
                )
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                SettingsNavRow(
                    icon = Icons.Default.Info,
                    iconColor = Color(0xFF0284C7),
                    title = "About AgriDevelop",
                    subtitle = "Version 1.0.0 • Smart Assistant for Farmers",
                    onClick = { }
                )
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                SettingsNavRow(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    iconColor = crimsonRed,
                    title = "Log Out",
                    subtitle = "Switch farmer account or sign out",
                    onClick = { showLogoutDialog = true }
                )
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log Out?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to sign out of your AgriDevelop profile?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogoutClick()
                }) {
                    Text("Log Out", color = crimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF334155),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(4.dp, RoundedCornerShape(18.dp), ambientColor = Color.Black.copy(alpha = 0.04f)),
        shape = RoundedCornerShape(18.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            content()
        }
    }
}

@Composable
private fun SettingsNavRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = RoundedCornerShape(12.dp),
            color = iconColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = Color(0xFF94A3B8),
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = RoundedCornerShape(12.dp),
            color = iconColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF0F5A2C)
            )
        )
    }
}

@Composable
private fun ProfileMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    badgeColor: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier
            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = badgeColor.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(34.dp),
                shape = CircleShape,
                color = badgeColor.copy(alpha = 0.14f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text(text = title, fontSize = 11.sp, color = Color(0xFF64748B))
        }
    }
}