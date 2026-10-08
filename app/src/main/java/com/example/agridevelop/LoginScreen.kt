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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    val primaryDarkGreen = Color(0xFF0F5A2C)
    val lightBorderColor = Color(0xFFE2E8F0)
    val placeholderColor = Color(0xFF94A3B8)
    val textDarkColor = Color(0xFF1E293B)
    val subtextColor = Color(0xFF64748B)

    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Bottom Farm Scenery with Soft Gradient Blend Overlay
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

            // Smooth top-down gradient to seamlessly fade scenery into the white page background
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

        // Scrollable Page Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Top Curved Forest Green Header
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

            // 2. Larger, Prominent Floating Login Card
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
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textDarkColor
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Enter your credentials to continue",
                        fontSize = 13.sp,
                        color = subtextColor
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Mobile Number Input Row with +91 Country Badge
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

                    Spacer(modifier = Modifier.height(18.dp))

                    // Password Input Field
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

                    Spacer(modifier = Modifier.height(26.dp))

                    // Full-width Solid Green Login Button
                    Button(
                        onClick = {
                            if (mobileNumber.length < 10) {
                                Toast.makeText(context, "Please enter a valid 10-digit mobile number", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val validatedUser = AuthRepository.authenticate(mobileNumber, password)
                            if (validatedUser != null) {
                                onLoginSuccess(validatedUser.fullName, validatedUser.mobile)
                            } else {
                                Toast.makeText(context, "Invalid credentials. Please register or check details", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryDarkGreen)
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Bottom Sign Up Switch
                    Row(
                        modifier = Modifier.clickable { onNavigateToSignUp() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            fontSize = 13.sp,
                            color = subtextColor
                        )
                        Text(
                            text = "Sign Up",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryDarkGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(130.dp))
        }
    }
}