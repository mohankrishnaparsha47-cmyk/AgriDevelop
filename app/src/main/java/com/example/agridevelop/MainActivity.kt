package com.example.agridevelop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory



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
    fun region(lang: AppLanguage, loc: String = "Nalgonda, Telangana") =
        if (lang == AppLanguage.TELUGU) {
            when {
                loc.contains("Nalgonda", ignoreCase = true) -> "నల్గొండ, తెలంగాణ"
                loc.contains("Suryapet", ignoreCase = true) -> "సూర్యాపేట, తెలంగాణ"
                loc.contains("Hyderabad", ignoreCase = true) -> "హైదరాబాద్, తెలంగాణ"
                loc.contains("Warangal", ignoreCase = true) -> "వరంగల్, తెలంగాణ"
                loc.contains("Khammam", ignoreCase = true) -> "ఖమ్మం, తెలంగాణ"
                loc.contains("Karimnagar", ignoreCase = true) -> "కరీంనగర్, తెలంగాణ"
                loc.contains("Nizamabad", ignoreCase = true) -> "నిజామాబాద్, తెలంగాణ"
                loc.contains("Mahabubnagar", ignoreCase = true) -> "మహబూబ్‌నగర్, తెలంగాణ"
                else -> "$loc, భారతదేశం"
            }
        } else {
            "$loc, India"
        }
    fun farmerCategory(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "చిన్న & సన్నకారు రైతు" else "Small & Marginal Farmer"
    fun logout(lang: AppLanguage) = if (lang == AppLanguage.TELUGU) "లాగౌట్ అవ్వండి" else "Logout Account"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAppCheck.getInstance()
            .installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance(),
            )

        AuthRepository.init(this)
        val defaultLocation = "Nalgonda, Telangana"

        val activeUser = AuthRepository.getSavedSession()
        val startDestination = if (activeUser != null) {
            "dashboard/${activeUser.fullName}/${activeUser.mobile}/4.5/$defaultLocation"
        } else {
            "login"
        }

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                var currentLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable(route = "splash") {
                        SplashScreen {
                            navController.navigate(startDestination) {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }

                    composable(route = "login") {
                        LoginScreen(
                            onNavigateToSignUp = { navController.navigate("signup") }
                        ) { name, mobile ->
                            navController.navigate("dashboard/$name/$mobile/4.5/$defaultLocation") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }

                    composable(route = "signup") {
                        RegisterScreen(
                            onNavigateToLogin = { navController.popBackStack() },
                            onNavigateToOtp = { name, mobile, pass, land, loc ->
                                navController.navigate("otp_screen/$mobile?name=$name&pass=$pass&land=$land&loc=$loc")
                            }
                        )
                    }

                    composable(
                        route = "otp_screen/{phoneNumber}?name={name}&pass={pass}&land={land}&loc={loc}",
                        arguments = listOf(
                            navArgument("phoneNumber") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType; defaultValue = "Farmer" },
                            navArgument("pass") { type = NavType.StringType; defaultValue = "" },
                            navArgument("land") { type = NavType.StringType; defaultValue = "4.5" },
                            navArgument("loc") { type = NavType.StringType; defaultValue = defaultLocation }
                        )
                    ) { backStackEntry ->
                        val phone = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                        val name = backStackEntry.arguments?.getString("name") ?: "Farmer"
                        val pass = backStackEntry.arguments?.getString("pass") ?: ""
                        val land = backStackEntry.arguments?.getString("land") ?: "4.5"
                        val loc = backStackEntry.arguments?.getString("loc") ?: defaultLocation

                        OtpScreen(
                            phoneNumber = phone,
                            onBack = { navController.popBackStack() },
                            onVerificationSuccess = { verifiedPhone ->
                                    if (pass.isNotEmpty()) {
                                        AuthRepository.register(
                                            mobile = verifiedPhone,
                                            name = name,
                                            pass = pass
                                        )
                                    }
                                navController.navigate("dashboard/$name/$verifiedPhone/$land/$loc") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(route = "dashboard/{name}/{mobile}/{landSize}/{location}") { backStackEntry ->
                        val farmerName = backStackEntry.arguments?.getString("name") ?: "Farmer"
                        val farmerMobile = backStackEntry.arguments?.getString("mobile") ?: "9876543210"
                        val landSize = backStackEntry.arguments?.getString("landSize") ?: "4.5"
                        val initialLocation = backStackEntry.arguments?.getString("location") ?: defaultLocation

                        MainAppShell(
                            navController = navController,
                            farmerName = farmerName,
                            farmerMobile = farmerMobile,
                            landSize = landSize,
                            initialLocation = initialLocation,
                            currentLanguage = currentLanguage,
                            onLanguageToggle = {
                                currentLanguage = if (currentLanguage == AppLanguage.ENGLISH) AppLanguage.TELUGU else AppLanguage.ENGLISH
                            }
                        )
                    }

                    composable(route = "market_prices/{location}") { backStackEntry ->
                        val loc = backStackEntry.arguments?.getString("location") ?: "Nalgonda, Telangana"
                        MarketPriceScreen(
                            locationName = loc,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(route = "market_prices") {
                        MarketPriceScreen(onBack = { navController.popBackStack() })
                    }

                    composable(route = "weather_alerts/{location}") { backStackEntry ->
                        val loc = backStackEntry.arguments?.getString("location") ?: "Nalgonda, Telangana"
                        WeatherAlertsScreen(
                            locationName = loc,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(route = "soil_list") {
                        SoilListScreen(
                            onBack = { navController.popBackStack() },
                            onSoilClick = { id -> navController.navigate("soil_detail/$id") }
                        )
                    }
                    composable(
                        route = "soil_detail/{soilId}",
                        arguments = listOf(navArgument("soilId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val soilId = backStackEntry.arguments?.getString("soilId") ?: "black_soil"
                        SoilDetailScreen(
                            soilId = soilId,
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
    landSize: String,
    initialLocation: String,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var currentLocation by remember { mutableStateOf(initialLocation) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 10.dp,
                modifier = Modifier.shadow(12.dp)
            ) {
                // Home
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "Home")
                    },
                    label = {
                        Text(AppStrings.homeTab(currentLanguage), fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AgriTheme.EmeraldPrimary,
                        selectedTextColor = AgriTheme.EmeraldPrimary,
                        indicatorColor = AgriTheme.MintBackground
                    )
                )
                // Profile
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(if (selectedTab == 1) Icons.Filled.Person else Icons.Outlined.Person, contentDescription = "Profile")
                    },
                    label = {
                        Text(AppStrings.profileTab(currentLanguage), fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AgriTheme.EmeraldPrimary,
                        selectedTextColor = AgriTheme.EmeraldPrimary,
                        indicatorColor = AgriTheme.MintBackground
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
                    location = currentLocation,
                    currentLanguage = currentLanguage,
                    onLanguageToggle = onLanguageToggle
                )
                1 -> FarmerProfileContent(
                    navController = navController,
                    farmerName = farmerName,
                    farmerMobile = farmerMobile,
                    landSize = landSize,
                    location = currentLocation,
                    onLocationChange = { newLoc -> currentLocation = newLoc },
                    currentLanguage = currentLanguage,
                    onLanguageToggle = onLanguageToggle,
                    onLogout = {
                        AuthRepository.logout()
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
    location: String,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit
) {
    val isTelugu = currentLanguage == AppLanguage.TELUGU
    val districtShort = location.substringBefore(",")

    // Colorful, contrasting tile configurations
    val services = remember(currentLanguage, location) {
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
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFE0F2FE)),
                iconTint = Color(0xFF0284C7),
                badgeText = if (isTelugu) "AI స్కానర్" else "AI Diagnostic",
                destinationRoute = "ai_detection"
            ),
            DashboardService(
                title = if (isTelugu) "మార్కెట్ ధరలు" else "Market Prices",
                subtitle = if (isTelugu) "మండి ధరలు ($districtShort)" else "Mandi Rates ($districtShort)",
                icon = Icons.Default.Storefront,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFF3E8FF)),
                iconTint = Color(0xFF7C3AED),
                badgeText = if (isTelugu) "రోజువారీ ధరలు" else "Daily Mandi",
                destinationRoute = "market_prices/$location"
            ),
            DashboardService(
                title = if (isTelugu) "ప్రభుత్వ పథకాలు" else "Govt Schemes",
                subtitle = if (isTelugu) "రైతు భరోసా & సబ్సిడీలు" else "Financial Aid & Subsidies",
                icon = Icons.Default.AccountBalance,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFF7ED)),
                iconTint = Color(0xFFEA580C),
                badgeText = if (isTelugu) "రాయితీలు" else "Subsidies",
                destinationRoute = "govt_schemes"
            ),
            DashboardService(
                title = if (isTelugu) "సాగు వీడియోలు" else "Farming Videos",
                subtitle = if (isTelugu) "ఆధునిక సాగు మెళకువలు" else "Modern Agri Tutorials",
                icon = Icons.Default.PlayCircle,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFCE7F3)),
                iconTint = Color(0xFFDB2777),
                badgeText = if (isTelugu) "వీడియోలు" else "Tutorials",
                destinationRoute = "farming_videos"
            ),
            DashboardService(
                title = if (isTelugu) "వాతావరణం" else "Weather Updates",
                subtitle = if (isTelugu) "వర్ష సూచన ($districtShort)" else "Rain Forecast ($districtShort)",
                icon = Icons.Default.WbSunny,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFEF9C3)),
                iconTint = Color(0xFFCA8A04),
                badgeText = if (isTelugu) "వాతావరణం" else "Weather",
                destinationRoute = "weather_alerts/$location"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AgriTheme.CanvasBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Enhanced Top Green Gradient Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AgriTheme.HeaderGradient,
                        shape = RoundedCornerShape(bottomStart = 34.dp, bottomEnd = 34.dp)
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 40.dp, bottom = 26.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier
                                    .size(48.dp)
                                    .shadow(6.dp, CircleShape),
                                shape = CircleShape,
                                color = Color.White
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.agri_logo),
                                    contentDescription = "AgriDevelop Logo",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = AppStrings.appTitle(currentLanguage),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = AppStrings.appSubtitle(currentLanguage),
                                    fontSize = 11.sp,
                                    color = AgriTheme.MintAccent,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Language Toggle Chip
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onLanguageToggle() },
                            color = Color.White.copy(alpha = 0.20f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.40f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Language, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
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

                    // Farmer Hero Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(22.dp)),
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White.copy(alpha = 0.16f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.30f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(2.dp, AgriTheme.MintAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = AgriTheme.EmeraldPrimary, modifier = Modifier.size(30.dp))
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
                                    text = "+91 $farmerMobile • ${AppStrings.region(currentLanguage, location)}",
                                    fontSize = 12.sp,
                                    color = Color(0xFFE8F5E9),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Services Grid Section
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier
                                .size(4.dp, 18.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = AgriTheme.EmeraldPrimary
                        ) {}
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.servicesHeader(currentLanguage),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B3824)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AgriTheme.EmeraldPrimary.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = AppStrings.toolsCount(currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.EmeraldPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(515.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    userScrollEnabled = false
                ) {
                    items(services) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(155.dp)
                                .clickable { navController.navigate(item.destinationRoute) }
                                .shadow(4.dp, RoundedCornerShape(22.dp)),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(item.gradientColors))
                                    .border(1.dp, AgriTheme.BorderSubtle, RoundedCornerShape(22.dp))
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
                                            color = item.iconTint.copy(alpha = 0.12f),
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
}

@Composable
fun FarmerProfileContent(
    navController: NavController,
    farmerName: String,
    farmerMobile: String,
    landSize: String = "4.5",
    location: String = "Nalgonda, Telangana",
    onLocationChange: (String) -> Unit,
    currentLanguage: AppLanguage,
    onLanguageToggle: () -> Unit,
    onLogout: () -> Unit
) {
    val isTelugu = currentLanguage == AppLanguage.TELUGU
    val crimsonRed = Color(0xFFDC2626)

    var notificationsEnabled by remember { mutableStateOf(value = true) }
    var showLogoutDialog by remember { mutableStateOf(value = false) }
    var showLocationDialog by remember { mutableStateOf(value = false) }

    val districts = listOf(
        "Nalgonda, Telangana" to "నల్గొండ",
        "Suryapet, Telangana" to "సూర్యాపేట",
        "Khammam, Telangana" to "ఖమ్మం",
        "Warangal, Telangana" to "వరంగల్",
        "Karimnagar, Telangana" to "కరీంనగర్",
        "Nizamabad, Telangana" to "నిజామాబాద్",
        "Mahabubnagar, Telangana" to "మహబూబ్‌నగర్",
        "Hyderabad, Telangana" to "హైదరాబాద్"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgriTheme.CanvasBg)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Gradient Hero Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AgriTheme.SunriseGlow,
                    shape = RoundedCornerShape(bottomStart = 34.dp, bottomEnd = 34.dp)
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

                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(3.dp, AgriTheme.GoldenHarvest, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.agri_logo),
                        contentDescription = "Profile Emblem",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = farmerName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showLocationDialog = true },
                    color = Color.White.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Place, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+91 $farmerMobile • ${AppStrings.region(currentLanguage, location)}",
                            color = Color(0xFFE8F5E9),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(12.dp))
                    }
                }
            }
        }

        // Floating Statistics Row
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
                value = if (isTelugu) "$landSize ఎకరాలు" else "$landSize Acres",
                badgeColor = AgriTheme.GoldenHarvest,
                icon = Icons.Default.Landscape
            )
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                title = if (isTelugu) "ప్రధాన పంట" else "Main Crop",
                value = if (isTelugu) "వరి / పత్తి" else "Paddy / Cotton",
                badgeColor = AgriTheme.EmeraldPrimary,
                icon = Icons.Default.Agriculture
            )
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                title = if (isTelugu) "రైతు హోదా" else "Status",
                value = if (isTelugu) "ధృవీకరించబడింది" else "Verified",
                badgeColor = AgriTheme.RiverCyan,
                icon = Icons.Default.Verified
            )
        }

        ProfileSectionTitle(if (isTelugu) "యాప్ సెట్టింగ్‌లు" else "App Settings")
        ProfileCardWrapper {
            ProfileSwitchRow(
                icon = Icons.Default.NotificationsActive,
                iconColor = AgriTheme.GoldenHarvest,
                title = if (isTelugu) "పంట & వాతావరణ సమాచారం" else "Crop & Weather Alerts",
                subtitle = if (isTelugu) "రోజువారీ హెచ్చరికలు & ధరలు" else "Daily rain and market notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            ProfileClickableRow(
                icon = Icons.Default.Place,
                iconColor = AgriTheme.RiverCyan,
                title = if (isTelugu) "ప్రాంతం / జిల్లా మార్చండి" else "Change Region / District",
                subtitle = AppStrings.region(currentLanguage, location),
                trailingBadge = if (isTelugu) "మార్చు" else "Edit",
                onClick = { showLocationDialog = true }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

            ProfileClickableRow(
                icon = Icons.Default.Language,
                iconColor = AgriTheme.RoyalPurple,
                title = if (isTelugu) "యాప్ భాష" else "Language",
                subtitle = if (isTelugu) "తెలుగు (మార్చడానికి నొక్కండి)" else "English (Tap to switch)",
                trailingBadge = if (isTelugu) "ENG" else "తెలుగు",
                onClick = onLanguageToggle
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        ProfileSectionTitle(if (isTelugu) "వ్యవసాయ వివరాలు" else "Farm Records & Preferences")
        ProfileCardWrapper {
            ProfileClickableRow(
                icon = Icons.Default.Eco,
                iconColor = AgriTheme.EmeraldPrimary,
                title = if (isTelugu) "నా పంటలు & నేల వివరాలు" else "My Crops & Soil Details",
                subtitle = AppStrings.farmerCategory(currentLanguage),
                onClick = { navController.navigate("soil_list") }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            ProfileClickableRow(
                icon = Icons.Default.Storefront,
                iconColor = AgriTheme.SunsetOrange,
                title = if (isTelugu) "ప్రాంతీయ మార్కెట్లు" else "Saved Mandis & Yards",
                subtitle = if (isTelugu) "మండి ధరలు (${location.substringBefore(",")})" else "Mandi prices for ${location.substringBefore(",")}",
                onClick = { navController.navigate("market_prices/$location") }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        ProfileSectionTitle(if (isTelugu) "సహాయం & ఖాతా" else "Support & Account")
        ProfileCardWrapper {
            ProfileClickableRow(
                icon = Icons.Default.SupportAgent,
                iconColor = AgriTheme.RoyalPurple,
                title = if (isTelugu) "కిసాన్ హెల్ప్‌లైన్" else "Kisan Helpdesk",
                subtitle = "1800-180-1551 (Toll-free)",
                onClick = { }
            )
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            ProfileClickableRow(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                iconColor = crimsonRed,
                title = AppStrings.logout(currentLanguage),
                subtitle = if (isTelugu) "ఖాతా నుండి నిష్క్రమించండి" else "Sign out of your profile",
                onClick = { showLogoutDialog = true }
            )
        }
    }

    if (showLocationDialog) {
        AlertDialog(
            onDismissRequest = { showLocationDialog = false },
            title = {
                Text(
                    text = if (isTelugu) "మీ జిల్లా / మండి మార్కెట్ ఎంచుకోండి" else "Select Your District / Mandi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 350.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    districts.forEach { (engName, telName) ->
                        val isSelected = location.equals(engName, ignoreCase = true)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onLocationChange(engName)
                                    showLocationDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Place,
                                contentDescription = null,
                                tint = if (isSelected) AgriTheme.EmeraldPrimary else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (isTelugu) "$telName (${engName.substringBefore(",")})" else engName.substringBefore(","),
                                fontSize = 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) AgriTheme.EmeraldPrimary else Color(0xFF1E293B)
                            )
                        }
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showLocationDialog = false }) {
                    Text(if (isTelugu) "రద్దు చేయి" else "Cancel", color = Color.Gray)
                }
            }
        )
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
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                ) {
                    Text(
                        text = if (isTelugu) "లాగౌట్" else "Log Out",
                        color = crimsonRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(text = if (isTelugu) "రద్దు చేయి" else "Cancel", color = Color.Gray)
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
            .padding(horizontal = 18.dp)
            .shadow(4.dp, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = Color.White
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
                checkedTrackColor = AgriTheme.EmeraldPrimary
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
        modifier = modifier.shadow(4.dp, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = badgeColor.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text(text = title, fontSize = 10.sp, color = Color(0xFF64748B))
        }
    }
}