package com.example.agridevelop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
    onRegisterSuccess: (name: String, mobile: String) -> Unit
) {
    val forestGreen = Color(0xFF0F5A2C)
    val leafGreen = Color(0xFF2E7D32)
    val fieldBg = Color(0xFFF9FBF9)
    val fieldBorder = Color(0xFFDDE7DF)

    var farmerName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFF2F8F3), Color(0xFFFFFFFF), Color(0xFFEAF5EC))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(bottom = 145.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(34.dp))

            // --- Brand Logo ---
            Image(
                painter = painterResource(id = R.drawable.agri_logo),
                contentDescription = "AgriDevelop Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 4.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "AgriDevelop",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = forestGreen,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Better Farming • Brighter Future",
                fontSize = 12.sp,
                color = leafGreen,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(22.dp))

            // --- Modern Card Container ---
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(26.dp),
                        ambientColor = forestGreen.copy(alpha = 0.08f),
                        spotColor = forestGreen.copy(alpha = 0.15f)
                    ),
                shape = RoundedCornerShape(26.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Create your farmer profile to get started",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Full Name Input
                    OutlinedTextField(
                        value = farmerName,
                        onValueChange = {
                            farmerName = it
                            errorMessage = null
                        },
                        label = { Text("Full Name", fontSize = 13.sp) },
                        placeholder = { Text("Enter your full name", color = Color(0xFFA0AEC0), fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = forestGreen, modifier = Modifier.size(20.dp))
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = fieldBg,
                            unfocusedContainerColor = fieldBg,
                            focusedBorderColor = forestGreen,
                            unfocusedBorderColor = fieldBorder
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone Row with Prefix
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .height(56.dp)
                                .width(96.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = fieldBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, fieldBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "🇮🇳", fontSize = 18.sp)
                                Text(text = "+91", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF718096), modifier = Modifier.size(16.dp))
                            }
                        }

                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { input ->
                                if (input.length <= 10 && input.all { it.isDigit() }) {
                                    mobileNumber = input
                                    errorMessage = null
                                }
                            },
                            label = { Text("Mobile Number", fontSize = 13.sp) },
                            placeholder = { Text("10 digit number", color = Color(0xFFA0AEC0), fontSize = 13.sp) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = fieldBg,
                                unfocusedContainerColor = fieldBg,
                                focusedBorderColor = forestGreen,
                                unfocusedBorderColor = fieldBorder
                            ),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        label = { Text("Password", fontSize = 13.sp) },
                        placeholder = { Text("At least 4 characters", color = Color(0xFFA0AEC0), fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = forestGreen, modifier = Modifier.size(20.dp))
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = Color(0xFF718096),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = fieldBg,
                            unfocusedContainerColor = fieldBg,
                            focusedBorderColor = forestGreen,
                            unfocusedBorderColor = fieldBorder
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = it, color = Color(0xFFDC2626), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    // Sign Up Button
                    Button(
                        onClick = {
                            errorMessage = null
                            if (farmerName.isBlank()) {
                                errorMessage = "Please enter your full name."
                                return@Button
                            }
                            if (mobileNumber.length != 10) {
                                errorMessage = "Please enter a valid 10-digit mobile number."
                                return@Button
                            }
                            if (password.length < 4) {
                                errorMessage = "Password must be at least 4 characters."
                                return@Button
                            }
                            val registered = AuthRepository.register(mobileNumber, farmerName.trim(), password)
                            if (registered) {
                                onRegisterSuccess(farmerName.trim(), mobileNumber)
                            } else {
                                errorMessage = "This mobile number is already registered."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = forestGreen),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
                    ) {
                        Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onNavigateToLogin() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Already have an account? ", fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("Login", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = forestGreen)
                    }
                }
            }
        }

        // --- Bottom Farm Banner ---
        Image(
            painter = painterResource(id = R.drawable.login_farm_banner),
            contentDescription = "Farm Field Scenery",
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )
    }
}