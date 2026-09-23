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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: (name: String, mobile: String) -> Unit
) {
    val forestGreen = Color(0xFF0F5A2C)
    val leafGreen = Color(0xFF2E7D32)
    val fieldBg = Color(0xFFF9FBF9)
    val fieldBorder = Color(0xFFDDE7DF)

    var mobile by remember { mutableStateOf("") }
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
            Spacer(modifier = Modifier.height(44.dp))

            // --- Brand Logo & Header ---
            Image(
                painter = painterResource(id = R.drawable.agri_logo),
                contentDescription = "AgriDevelop Logo",
                modifier = Modifier
                    .size(125.dp)
                    .padding(bottom = 6.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "AgriDevelop",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = forestGreen,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "Better Farming • Brighter Future",
                fontSize = 13.sp,
                color = leafGreen,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(26.dp))

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
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Login to access your farm dashboard",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // Phone Input
                    OutlinedTextField(
                        value = mobile,
                        onValueChange = { if (it.length <= 10 && it.all { ch -> ch.isDigit() }) mobile = it },
                        label = { Text("Phone Number", fontSize = 13.sp) },
                        placeholder = { Text("10 digit mobile number", color = Color(0xFFA0AEC0), fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = forestGreen, modifier = Modifier.size(20.dp))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", fontSize = 13.sp) },
                        placeholder = { Text("Enter your password", color = Color(0xFFA0AEC0), fontSize = 13.sp) },
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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Gradient Submit Button
                    Button(
                        onClick = {
                            if (mobile.isBlank() || password.isBlank()) {
                                errorMessage = "Please enter both mobile and password."
                                return@Button
                            }
                            val user = AuthRepository.authenticate(mobile.trim(), password.trim())
                            if (user != null) {
                                onLoginSuccess(user.fullName, user.mobile)
                            } else {
                                errorMessage = "Invalid mobile number or password."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = forestGreen),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
                    ) {
                        Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onNavigateToSignUp() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account? ", fontSize = 13.sp, color = Color(0xFF64748B))
                        Text("Sign Up", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = forestGreen)
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