package com.example.agridevelop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agridevelop.R

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: (name: String, mobile: String) -> Unit
) {
    val forestGreen = Color(0xFF0F5A2C)
    val fieldBg = Color(0xFFF9FBF9)
    val fieldBorder = Color(0xFFDDE7DF)

    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Big, Bold Text Styles for clear readability
    val largeInputStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF1E293B)
    )

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
                .padding(horizontal = 20.dp)
                .padding(bottom = 95.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- Green Banner Header for Logo & Title ---
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = forestGreen,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth(0.94f)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agri_logo),
                            contentDescription = "AgriDevelop Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "AgriDevelop",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Better Farming • Brighter Future",
                        fontSize = 14.sp,
                        color = Color(0xFFC8E6C9),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Main Card Container ---
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
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Enter your credentials to continue",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Phone Row with Large Prefix
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .height(62.dp)
                                .width(98.dp),
                            shape = RoundedCornerShape(14.dp),
                            color = fieldBg,
                            border = androidx.compose.foundation.BorderStroke(1.2.dp, fieldBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "🇮🇳", fontSize = 20.sp)
                                Text(text = "+91", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1E293B))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF718096), modifier = Modifier.size(20.dp))
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
                            textStyle = largeInputStyle,
                            label = { Text("Mobile Number", fontSize = 15.sp, fontWeight = FontWeight.Medium) },
                            placeholder = { Text("10 digit number", color = Color(0xFFA0AEC0), fontSize = 16.sp) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = fieldBg,
                                unfocusedContainerColor = fieldBg,
                                focusedBorderColor = forestGreen,
                                unfocusedBorderColor = fieldBorder
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(62.dp),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        textStyle = largeInputStyle,
                        label = { Text("Password", fontSize = 15.sp, fontWeight = FontWeight.Medium) },
                        placeholder = { Text("At least 4 characters", color = Color(0xFFA0AEC0), fontSize = 16.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = forestGreen, modifier = Modifier.size(22.dp))
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = Color(0xFF718096),
                                    modifier = Modifier.size(22.dp)
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),
                        shape = RoundedCornerShape(14.dp)
                    )

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, color = Color(0xFFDC2626), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Login Button
                    Button(
                        onClick = {
                            errorMessage = null
                            if (mobileNumber.length != 10) {
                                errorMessage = "Please enter a valid 10-digit mobile number."
                                return@Button
                            }
                            if (password.length < 4) {
                                errorMessage = "Password must be at least 4 characters."
                                return@Button
                            }
                            val farmer = AuthRepository.authenticate(mobileNumber, password)
                            if (farmer != null) {
                                onLoginSuccess(farmer.fullName, farmer.mobile)
                            } else {
                                errorMessage = "Invalid mobile number or password."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(6.dp, RoundedCornerShape(14.dp), spotColor = forestGreen),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
                    ) {
                        Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onNavigateToSignUp() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account? ", fontSize = 15.sp, color = Color(0xFF64748B))
                        Text("Sign Up", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = forestGreen)
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
                .height(105.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )
    }
}