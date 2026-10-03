package com.example.agridevelop

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    // Hold splash screen for 3 seconds then proceed
    LaunchedEffect(Unit) {
        delay(3000L)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Full screen background poster
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = "AgriDevelop Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Circular spinning dotted loader with text at bottom center
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DottedCircularSpinner()
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Loading...",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun DottedCircularSpinner(
    modifier: Modifier = Modifier,
    dotCount: Int = 12,
    radius: Float = 34f,
    dotRadius: Float = 4.2f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "DotSpinnerTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RotationAngle"
    )

    Canvas(
        modifier = modifier
            .size(46.dp)
            .graphicsLayer { rotationZ = angle }
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val step = (2 * Math.PI / dotCount).toFloat()

        for (i in 0 until dotCount) {
            val currentAngle = i * step
            val x = center.x + radius * cos(currentAngle)
            val y = center.y + radius * sin(currentAngle)
            // Fading alpha gradient across the circle
            val alpha = (i + 1f) / dotCount

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = dotRadius,
                center = Offset(x, y)
            )
        }
    }
}