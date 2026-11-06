package com.example.agridevelop

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    phoneNumber: String,
    onBack: () -> Unit,
    onVerificationSuccess: (phone: String) -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val forestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)
    val context = LocalContext.current

    val demoOtp = "123456"

    var otpCode by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var timerSeconds by remember { mutableIntStateOf(60) }

    LaunchedEffect(timerSeconds) {
        if (timerSeconds > 0) {
            delay(1000L)
            timerSeconds--
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Verify OTP",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkForest
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBg)
                .padding(padding)
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            Surface(
                modifier = Modifier.size(76.dp),
                shape = CircleShape,
                color = forestGreen.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.LockReset,
                        contentDescription = null,
                        tint = forestGreen,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                "Enter 6-Digit OTP",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Enter the verification code for",
                fontSize = 13.sp,
                color = Color(0xFF64748B)
            )

            Text(
                "+91 $phoneNumber",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = forestGreen
            )

            Spacer(Modifier.height(18.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFDCFCE7)
            ) {
                Text(
                    "Demo OTP: $demoOtp",
                    color = Color(0xFF15803D),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(Modifier.height(22.dp))

            OutlinedTextField(
                value = otpCode,
                onValueChange = {
                    if (it.length <= 6 && it.all { char ->
                            char.isDigit()
                        }) {
                        otpCode = it
                        errorMessage = null
                    }
                },
                placeholder = {
                    Text(
                        "------",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 10.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )

            errorMessage?.let {
                Spacer(Modifier.height(10.dp))
                Text(
                    it,
                    color = Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (otpCode == demoOtp) {
                        isLoading = true
                        errorMessage = null

                        Toast.makeText(
                            context,
                            "OTP Verified Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        onVerificationSuccess(phoneNumber)
                    } else {
                        errorMessage = "Incorrect OTP. Please enter 123456."
                    }
                },
                enabled = !isLoading && otpCode.length == 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(4.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = forestGreen
                )
            ) {
                Text(
                    "Verify & Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Demo verification ",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )

                if (timerSeconds > 0) {
                    Text(
                        "Resend in ${timerSeconds}s",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF94A3B8)
                    )
                } else {
                    TextButton(
                        onClick = {
                            otpCode = ""
                            errorMessage = null
                            timerSeconds = 60
                        }
                    ) {
                        Text(
                            "Reset OTP",
                            color = forestGreen
                        )
                    }
                }
            }
        }
    }
}