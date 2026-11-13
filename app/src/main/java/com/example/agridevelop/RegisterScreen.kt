package com.example.agridevelop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToOtp: (name: String, mobile: String, pass: String, land: String, loc: String) -> Unit
) {
    val context = LocalContext.current
    val dbHelper = remember { AgriDatabaseHelper(context) }

    val primaryDarkGreen = Color(0xFF0F5A2C)
    val lightBorderColor = Color(0xFFE2E8F0)
    val placeholderColor = Color(0xFF94A3B8)
    val textDarkColor = Color(0xFF1E293B)
    val subtextColor = Color(0xFF64748B)

    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var landSize by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("") }
    var showDistrictDialog by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val districtOptions = listOf(
        "Nalgonda, Telangana",
        "Suryapet, Telangana",
        "Khammam, Telangana",
        "Warangal, Telangana",
        "Karimnagar, Telangana",
        "Nizamabad, Telangana",
        "Mahabubnagar, Telangana",
        "Hyderabad, Telangana"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_farm_banner),
                contentDescription = "Farm scenery",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White.copy(alpha = 0.85f),
                                Color.White.copy(alpha = 0.0f)
                            )
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                    .background(primaryDarkGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 18.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .size(68.dp)
                            .shadow(6.dp, CircleShape),
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agri_logo),
                            contentDescription = "AgriDevelop Emblem",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "AgriDevelop",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Better Farming • Brighter Future",
                        fontSize = 13.sp,
                        color = Color(0xFFD1E7D5),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = Color.Black.copy(alpha = 0.12f)
                    ),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Register",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textDarkColor
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Create your farmer profile to get started",
                        fontSize = 13.sp,
                        color = subtextColor
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Full Name Field
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = {
                            Text("Full Name (రైతు పేరు)", fontSize = 14.sp, color = placeholderColor)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = primaryDarkGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryDarkGreen,
                            unfocusedBorderColor = lightBorderColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mobile Field
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .height(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.2.dp, lightBorderColor, RoundedCornerShape(14.dp)),
                            color = Color.White
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🇮🇳", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "+91",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textDarkColor
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { if (it.length <= 10) mobileNumber = it },
                            placeholder = {
                                Text("Mobile Number", fontSize = 14.sp, color = placeholderColor)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryDarkGreen,
                                unfocusedBorderColor = lightBorderColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text("Password", fontSize = 14.sp, color = placeholderColor)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = primaryDarkGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryDarkGreen,
                            unfocusedBorderColor = lightBorderColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Land Size Field
                    OutlinedTextField(
                        value = landSize,
                        onValueChange = { landSize = it },
                        placeholder = {
                            Text("Land Size (e.g., 4.5)", fontSize = 14.sp, color = placeholderColor)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Landscape,
                                contentDescription = null,
                                tint = primaryDarkGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFE8F5E9),
                                modifier = Modifier.padding(end = 12.dp)
                            ) {
                                Text(
                                    text = "Acres / ఎకరాలు",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryDarkGreen,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryDarkGreen,
                            unfocusedBorderColor = lightBorderColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // District Selection
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDistrictDialog = true }
                    ) {
                        OutlinedTextField(
                            value = selectedLocation,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            placeholder = {
                                Text("District", fontSize = 14.sp, color = placeholderColor)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Place,
                                    contentDescription = null,
                                    tint = primaryDarkGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Select District",
                                    tint = textDarkColor,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = textDarkColor,
                                disabledBorderColor = lightBorderColor,
                                disabledContainerColor = Color.White,
                                disabledLeadingIconColor = primaryDarkGreen,
                                disabledTrailingIconColor = textDarkColor,
                                disabledPlaceholderColor = placeholderColor
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    // Save directly into SQLite Table
                    Button(
                        onClick = {
                            val trimmedName = fullName.trim()
                            val trimmedMobile = mobileNumber.trim()
                            val trimmedPass = password.trim()

                            if (trimmedName.isEmpty()) {
                                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (trimmedMobile.length != 10) {
                                Toast.makeText(context, "Please enter a valid 10-digit mobile number", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (trimmedPass.length < 4) {
                                Toast.makeText(context, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Prevent duplicate registration
                            if (dbHelper.getUserName(trimmedMobile) != null) {
                                Toast.makeText(context, "Mobile number already registered! Please login.", Toast.LENGTH_LONG).show()
                                onNavigateToLogin()
                                return@Button
                            }

                            val validLand = if (landSize.trim().isEmpty()) "4.5" else landSize.trim()
                            val validLoc = if (selectedLocation.trim().isEmpty()) "Nalgonda, Telangana" else selectedLocation.trim()

                            val isSaved = dbHelper.registerFarmer(
                                mobile = trimmedMobile,
                                name = trimmedName,
                                pass = trimmedPass,
                                landSize = validLand,
                                location = validLoc
                            )

                            if (isSaved) {
                                Toast.makeText(context, "నమోదు విజయవంతమైంది! (Registered Successfully)", Toast.LENGTH_LONG).show()
                                onNavigateToLogin()
                            } else {
                                Toast.makeText(context, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryDarkGreen)
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickable { onNavigateToLogin() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            fontSize = 13.sp,
                            color = subtextColor
                        )
                        Text(
                            text = "Login",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryDarkGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(130.dp))
        }

        if (showDistrictDialog) {
            AlertDialog(
                onDismissRequest = { showDistrictDialog = false },
                title = {
                    Text(
                        text = "Select District",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = textDarkColor
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        districtOptions.forEach { district ->
                            val isSelected = selectedLocation == district
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedLocation = district
                                        showDistrictDialog = false
                                    }
                                    .padding(vertical = 12.dp, horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Place,
                                    contentDescription = null,
                                    tint = if (isSelected) primaryDarkGreen else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = district,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) primaryDarkGreen else textDarkColor
                                )
                            }
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showDistrictDialog = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        }
    }
}