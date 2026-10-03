package com.example.agridevelop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToOtp: (name: String, mobile: String, password: String, landSize: String, location: String) -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val forestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)

    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var landSize by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("Nalgonda, Telangana") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showLocationDialog by remember { mutableStateOf(false) }

    val districts = listOf(
        "Nalgonda, Telangana",
        "Suryapet, Telangana",
        "Khammam, Telangana",
        "Warangal, Telangana",
        "Karimnagar, Telangana",
        "Nizamabad, Telangana",
        "Mahabubnagar, Telangana",
        "Hyderabad, Telangana"
    )

    fun handleRegistration() {
        if (fullName.trim().isEmpty()) {
            errorMessage = "Please enter your full name."
            return
        }
        if (mobileNumber.trim().length != 10 || !mobileNumber.all { it.isDigit() }) {
            errorMessage = "Please enter a valid 10-digit mobile number."
            return
        }
        if (landSize.trim().isEmpty()) {
            errorMessage = "Please specify your farm / land size in acres."
            return
        }
        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters long."
            return
        }
        if (password != confirmPassword) {
            errorMessage = "Passwords do not match."
            return
        }

        errorMessage = null
        // Trigger OTP verification flow with pending registration data
        onNavigateToOtp(fullName.trim(), mobileNumber.trim(), password, landSize.trim(), selectedLocation)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Farmer Registration", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateToLogin) {
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(68.dp),
                shape = CircleShape,
                color = forestGreen.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = null,
                        tint = forestGreen,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Create Farmer Account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
            Text(
                text = "Enter your details to verify via SMS OTP",
                fontSize = 13.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Full Name Field
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it; errorMessage = null },
                label = { Text("Full Name (రైతు పేరు)") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = forestGreen) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = forestGreen,
                    unfocusedBorderColor = Color(0xFFDDE7DF)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mobile Number Field
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = {
                    if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                        mobileNumber = it
                        errorMessage = null
                    }
                },
                label = { Text("Mobile Number (ఫోన్ నెంబర్)") },
                prefix = { Text("+91 ", fontWeight = FontWeight.Bold, color = forestGreen) },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = forestGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = forestGreen,
                    unfocusedBorderColor = Color(0xFFDDE7DF)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Land Size
            OutlinedTextField(
                value = landSize,
                onValueChange = { landSize = it; errorMessage = null },
                label = { Text("Land Size in Acres (భూమి విస్తీర్ణం)") },
                suffix = { Text("Acres", color = Color(0xFF64748B)) },
                leadingIcon = { Icon(Icons.Default.Landscape, contentDescription = null, tint = forestGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = forestGreen,
                    unfocusedBorderColor = Color(0xFFDDE7DF)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // District / Region Selector
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { showLocationDialog = true },
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDE7DF))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = forestGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "District / Mandi Location", fontSize = 11.sp, color = Color(0xFF64748B))
                        Text(text = selectedLocation, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                    }
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; errorMessage = null },
                label = { Text("Create Password (పాస్‌వర్డ్)") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = forestGreen) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = forestGreen,
                    unfocusedBorderColor = Color(0xFFDDE7DF)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Confirm Password Field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; errorMessage = null },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.LockReset, contentDescription = null, tint = forestGreen) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = forestGreen,
                    unfocusedBorderColor = Color(0xFFDDE7DF)
                )
            )

            errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = msg,
                    color = Color(0xFFDC2626),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Submit Button
            Button(
                onClick = { handleRegistration() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(4.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
            ) {
                Icon(Icons.Default.Sms, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Verify Mobile & Register", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account? ", fontSize = 14.sp, color = Color(0xFF64748B))
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = forestGreen,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }

    if (showLocationDialog) {
        AlertDialog(
            onDismissRequest = { showLocationDialog = false },
            title = { Text("Select Your District", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    districts.forEach { dist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedLocation = dist
                                    showLocationDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (selectedLocation == dist) Icons.Default.CheckCircle else Icons.Default.Place,
                                contentDescription = null,
                                tint = if (selectedLocation == dist) forestGreen else Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = dist, fontSize = 14.sp, color = Color(0xFF1E293B))
                        }
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showLocationDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}