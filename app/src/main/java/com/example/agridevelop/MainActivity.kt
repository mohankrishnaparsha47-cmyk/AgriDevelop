package com.example.agridevelop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class AppLanguage {
    ENGLISH, TELUGU
}

object AppStrings {
    fun appTitle(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "అగ్రి డెవలప్" else "AgriDevelop"
    fun appSubtitle(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "మెరుగైన వ్యవసాయం • ఉజ్వల భవిష్యత్తు" else "Better Farming • Brighter Future"
    fun greeting(lang: AppLanguage, name: String) = if (lang == AppLanguage.TELUGU) "నమస్కారం, $name" else "Namaste, $name"
    fun servicesHeader(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "వ్యవసాయ సేవలు" else "Agricultural Services"
    fun toolsCount(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "6 సేవలు" else "6 Tools"
    fun homeTab(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "హోమ్" else "Home"
    fun profileTab(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "ప్రొఫైల్" else "Profile"
    fun region(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "తెలంగాణ, భారతదేశం" else "Telangana, India"
    fun farmerCategory(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "చిన్న & సన్నకారు రైతు" else "Small & Marginal Farmer"
    fun logout(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "లాగౌట్ అవ్వండి" else "Logout Account"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize persistent SQLite database
        AuthRepository.init(this)

        // 2. Check if a farmer is already logged in
        val activeUser = AuthRepository.getSavedSession()
        val startDestination = if (activeUser != null) {
            "dashboard/${activeUser.fullName}/${activeUser.mobile}"
        } else {
            "login"
        }

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                var currentLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    // ... rest of your composables stay exactly as they are ...
                    // Login Route
                    composable(route = "login") {
                        LoginScreen(
                            onNavigateToSignUp = { navController.navigate("signup") },
                            onLoginSuccess = { name, mobile ->
                                navController.navigate("dashboard/$name/$mobile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // Sign Up Route
                    composable(route = "signup") {
                        RegisterScreen(
                            onNavigateToLogin = { navController.popBackStack() },
                            onRegisterSuccess = { name, mobile ->
                                navController.navigate("dashboard/$name/$mobile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // Main App Shell with Bottom Bar & Language State
                    composable(route = "dashboard/{name}/{mobile}") { backStackEntry ->
                        val farmerName = backStackEntry.arguments?.getString("name") ?: "Farmer"
                        val farmerMobile = backStackEntry.arguments?.getString("mobile") ?: "9876543210"

                        MainAppShell(
                            navController = navController,
                            farmerName = farmerName,
                            farmerMobile = farmerMobile,
                            currentLanguage = currentLanguage,
                            onLanguageToggle = {
                                currentLanguage = if (currentLanguage == AppLanguage.ENGLISH) AppLanguage.TELUGU else AppLanguage.ENGLISH
                            }
                        )
                    }

                    // Feature Screens
                    composable(route = "market_prices") {
                        MarketPriceScreen(onBack = { navController.popBackStack() })
                    }
                    composable(route = "soil_list") {
                        SoilListScreen(
                            onBack = { navController.popBackStack() },
                            onSoilClick = { name -> navController.navigate("soil_detail/$name") }
                        )
                    }
                    composable(route = "soil_detail/{soilName}") { backStackEntry ->
                        SoilDetailScreen(
                            soilName = backStackEntry.arguments?.getString("soilName") ?: "Black Soil",
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(route = "govt_schemes") {
                        SchemesScreen(onBack = { navController.popBackStack() })
                    }
                    composable(route = "farming_videos") {
                        VideosScreen(onBack = { navController.popBackStack() })
                    }
                    composable(route = "ai_detection") {
                        AIDetectionScreen(onBack = { navController.popBackStack() })
                    }
                    composable(route = "weather_alerts") {
                        WeatherAlertsScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

data class DashboardService(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val iconTint: Color,
    val badgeText: String,
    val destinationRoute: String
)

@Composable
fun MainAppShell(
    navController: NavController,
    farmerName: String,
    farmerMobile: String,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit
) {
    val brandForestGreen = Color(0xFF0F5A2C)
    var selectedTab by remember { mutableStateOf(0) } // 0 = Home, 1 = Profile

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.homeTab(currentLanguage),
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = brandForestGreen,
                        selectedTextColor = brandForestGreen,
                        indicatorColor = Color(0xFFE8F5E9)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            if (selectedTab == 1) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.profileTab(currentLanguage),
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = brandForestGreen,
                        selectedTextColor = brandForestGreen,
                        indicatorColor = Color(0xFFE8F5E9)
                    )
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> FarmerDashboardContent(
                    navController = navController,
                    farmerName = farmerName,
                    farmerMobile = farmerMobile,
                    currentLanguage = currentLanguage,
                    onLanguageToggle = onLanguageToggle
                )
                1 -> FarmerProfileContent(
                    farmerName = farmerName,
                    farmerMobile = farmerMobile,
                    currentLanguage = currentLanguage,
                    onLanguageToggle = onLanguageToggle,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FarmerDashboardContent(
    navController: NavController,
    farmerName: String,
    farmerMobile: String,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit
) {
    val brandForestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF6FAF6)

    val isTelugu = currentLanguage == AppLanguage.TELUGU

    val services = remember(currentLanguage) {
        listOf(
            DashboardService(
                title = if (isTelugu) "నేల రకాలు" else "Soil Types",
                subtitle = if (isTelugu) "నేల మరియు పంట సిఫార్సులు" else "Soil & Crop Match",
                icon = Icons.Default.Landscape,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFE8F5E9)),
                iconTint = Color(0xFF2E7D32),
                badgeText = if (isTelugu) "నేల ఆరోగ్యం" else "Soil Health",
                destinationRoute = "soil_list"
            ),
            DashboardService(
                title = if (isTelugu) "క్రాప్ డాక్టర్" else "Crop Doctor",
                subtitle = if (isTelugu) "AI తెగుళ్ల గుర్తింపు" else "AI Disease Scanner",
                icon = Icons.Default.CameraAlt,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFE3F2FD)),
                iconTint = Color(0xFF1565C0),
                badgeText = if (isTelugu) "AI స్కానర్" else "AI Diagnostic",
                destinationRoute = "ai_detection"
            ),
            DashboardService(
                title = if (isTelugu) "మార్కెట్ ధరలు" else "Market Prices",
                subtitle = if (isTelugu) "తాజా మార్కెట్ / మండి ధరలు" else "Live Mandi Rates",
                icon = Icons.Default.Storefront,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFF3E5F5)),
                iconTint = Color(0xFF7B1FA2),
                badgeText = if (isTelugu) "రోజువారీ ధరలు" else "Daily Mandi",
                destinationRoute = "market_prices"
            ),
            DashboardService(
                title = if (isTelugu) "ప్రభుత్వ పథకాలు" else "Govt Schemes",
                subtitle = if (isTelugu) "రైతు భరోసా & సబ్సిడీలు" else "Financial Aid & Subsidies",
                icon = Icons.Default.AccountBalance,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFF3E0)),
                iconTint = Color(0xFFE65100),
                badgeText = if (isTelugu) "రాయితీలు" else "Subsidies",
                destinationRoute = "govt_schemes"
            ),
            DashboardService(
                title = if (isTelugu) "సాగు వీడియోలు" else "Farming Videos",
                subtitle = if (isTelugu) "ఆధునిక సాగు మెళకువలు" else "Modern Agri Tutorials",
                icon = Icons.Default.PlayCircle,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFCE4EC)),
                iconTint = Color(0xFFC2185B),
                badgeText = if (isTelugu) "వీడియోలు" else "Tutorials",
                destinationRoute = "farming_videos"
            ),
            DashboardService(
                title = if (isTelugu) "వాతావరణం" else "Weather Updates",
                subtitle = if (isTelugu) "వర్ష సూచన & వాతావరణ హెచ్చరికలు" else "Rain & Climate Forecast",
                icon = Icons.Default.WbSunny,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFDE7)),
                iconTint = Color(0xFFF57F17),
                badgeText = if (isTelugu) "వాతావరణం" else "Weather",
                destinationRoute = "weather_alerts"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Nature Gradient Top Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F5A2C), Color(0xFF1B5E20), Color(0xFF2E7D32))
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(horizontal = 20.dp)
                .padding(top = 44.dp, bottom = 28.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = AppStrings.appTitle(currentLanguage),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = AppStrings.appSubtitle(currentLanguage),
                            fontSize = 12.sp,
                            color = Color(0xFFC8E6C9),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Language Switch Pill
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onLanguageToggle() },
                        color = Color.White.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.2.dp, Color.White.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Language",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (isTelugu) "తెలుగు" else "ENG",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Glassmorphic Profile Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = brandForestGreen,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = AppStrings.greeting(currentLanguage, farmerName),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "+91 $farmerMobile • ${AppStrings.region(currentLanguage)}",
                                fontSize = 12.sp,
                                color = Color(0xFFE8F5E9)
                            )
                        }
                    }
                }
            }
        }

        // Services Grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(top = 22.dp, bottom = 28.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppStrings.servicesHeader(currentLanguage),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3824)
                )
                Text(
                    text = AppStrings.toolsCount(currentLanguage),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = brandForestGreen
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(510.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                userScrollEnabled = false
            ) {
                items(services) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(155.dp)
                            .clickable { navController.navigate(item.destinationRoute) },
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(item.gradientColors))
                                .border(1.dp, Color(0xFFE0EAE2), RoundedCornerShape(20.dp))
                                .padding(14.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(item.iconTint.copy(alpha = 0.12f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.title,
                                            tint = item.iconTint,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }

                                    Surface(
                                        color = item.iconTint.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = item.badgeText,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = item.iconTint,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = item.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.subtitle,
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B),
                                        lineHeight = 14.sp
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

@Composable
fun FarmerProfileContent(
    farmerName: String,
    farmerMobile: String,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit,
    onLogout: () -> Unit
) {
    val isTelugu = currentLanguage == AppLanguage.TELUGU
    val primaryGreen = Color(0xFF0F5A2C)
    val emeraldGreen = Color(0xFF1B8A46)
    val sunnyAmber = Color(0xFFF59E0B)
    val vibrantBlue = Color(0xFF2563EB)
    val purpleAccent = Color(0xFF7C3AED)
    val crimsonRed = Color(0xFFDC2626)

    var notificationsEnabled by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // --- Gradient Hero Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(primaryGreen, emeraldGreen, Color(0xFF34D399))
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(top = 28.dp, bottom = 36.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = AppStrings.profileTab(currentLanguage),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Emblem Avatar Badge
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(3.dp, sunnyAmber, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = R.drawable.agri_logo),
                        contentDescription = "Profile Emblem",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = farmerName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "+91 $farmerMobile • ${AppStrings.region(currentLanguage)}",
                    color = Color(0xFFE8F5E9),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // --- Quick Highlights Badges ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-20).dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                title = if (isTelugu) "భూమి విస్తీర్ణం" else "Land Size",
                value = if (isTelugu) "4.5 ఎకరాలు" else "4.5 Acres",
                badgeColor = sunnyAmber,
                icon = Icons.Default.Landscape
            )
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                title = if (isTelugu) "ప్రధాన పంట" else "Main Crop",
                value = if (isTelugu) "వరి / పత్తి" else "Paddy / Cotton",
                badgeColor = emeraldGreen,
                icon = Icons.Default.Agriculture
            )
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                title = if (isTelugu) "రైతు హోదా" else "Status",
                value = if (isTelugu) "ధృవీకరించబడింది" else "Verified",
                badgeColor = vibrantBlue,
                icon = Icons.Default.Verified
            )
        }

        // --- App Settings ---
        ProfileSectionTitle(if (isTelugu) "యాప్ సెట్టింగ్‌లు" else "App Settings")
        ProfileCardWrapper {
            ProfileSwitchRow(
                icon = Icons.Default.NotificationsActive,
                iconColor = sunnyAmber,
                title = if (isTelugu) "పంట & వాతావరణ సమాచారం" else "Crop & Weather Alerts",
                subtitle = if (isTelugu) "రోజువారీ హెచ్చరికలు & ధరలు" else "Daily rain and market notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            ProfileClickableRow(
                icon = Icons.Default.Language,
                iconColor = vibrantBlue,
                title = if (isTelugu) "యాప్ భాష" else "Language",
                subtitle = if (isTelugu) "తెలుగు (మార్చడానికి నొక్కండి)" else "English (Tap to switch)",
                trailingBadge = if (isTelugu) "ENG" else "తెలుగు",
                onClick = onLanguageToggle
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // --- Farm Data & Records ---
        ProfileSectionTitle(if (isTelugu) "వ్యవసాయ వివరాలు" else "Farm Records & Preferences")
        ProfileCardWrapper {
            ProfileClickableRow(
                icon = Icons.Default.Eco,
                iconColor = emeraldGreen,
                title = if (isTelugu) "నా పంటలు & నేల వివరాలు" else "My Crops & Soil Details",
                subtitle = AppStrings.farmerCategory(currentLanguage),
                onClick = { }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            ProfileClickableRow(
                icon = Icons.Default.Storefront,
                iconColor = sunnyAmber,
                title = if (isTelugu) "ప్రాంతీయ మార్కెట్లు" else "Saved Mandis & Yards",
                subtitle = AppStrings.region(currentLanguage),
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // --- Support & Sign Out ---
        ProfileSectionTitle(if (isTelugu) "సహాయం & ఖాతా" else "Support & Account")
        ProfileCardWrapper {
            ProfileClickableRow(
                icon = Icons.Default.SupportAgent,
                iconColor = purpleAccent,
                title = if (isTelugu) "కిసాన్ హెల్ప్‌లైన్" else "Kisan Helpdesk",
                subtitle = "1800-180-1551 (Toll-free)",
                onClick = { }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            ProfileClickableRow(
                icon = Icons.Default.ExitToApp,
                iconColor = crimsonRed,
                title = AppStrings.logout(currentLanguage),
                subtitle = if (isTelugu) "ఖాతా నుండి నిష్క్రమించండి" else "Sign out of your profile",
                onClick = { showLogoutDialog = true }
            )
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = if (isTelugu) "లాగౌట్ చేయాలా?" else "Log Out?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = if (isTelugu)
                        "మీరు అగ్రి డెవలప్ ఖాతా నుండి నిష్క్రమించాలనుకుంటున్నారా?"
                    else
                        "Are you sure you want to sign out of your AgriDevelop profile?"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text(
                        text = if (isTelugu) "లాగౌట్" else "Log Out",
                        color = crimsonRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(
                        text = if (isTelugu) "రద్దు చేయి" else "Cancel",
                        color = Color.Gray
                    )
                }
            }
        )
    }
}

@Composable
private fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF334155),
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp)
    )
}

@Composable
private fun ProfileCardWrapper(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            content()
        }
    }
}

@Composable
private fun ProfileClickableRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    trailingBadge: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(38.dp),
            shape = RoundedCornerShape(10.dp),
            color = iconColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
        }

        if (trailingBadge != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = iconColor.copy(alpha = 0.12f)
            ) {
                Text(
                    text = trailingBadge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun ProfileSwitchRow(
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
            modifier = Modifier.size(38.dp),
            shape = RoundedCornerShape(10.dp),
            color = iconColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
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
private fun ProfileStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    badgeColor: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(30.dp),
                shape = CircleShape,
                color = badgeColor.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text(text = title, fontSize = 10.sp, color = Color(0xFF64748B))
        }
    }
}