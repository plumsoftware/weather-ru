package ru.plumsoftware.weatherforecastru.presentation.authorization.presentation

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.delay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        contentVisible = true
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
        }
    }

    val backgroundBrush = if (!isDarkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3D5AFE),
                Color(0xFF7B2FBE),
                Color(0xFF1A237E)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0B0F1A),
                Color(0xFF151A2E),
                Color(0xFF0D1B3E)
            )
        )
    }

    AnimatedVisibility(
        visible = contentVisible,
        enter = fadeIn(tween(500)) + slideInVertically(
            initialOffsetY = { it / 8 },
            animationSpec = tween(600, easing = EaseOutCubic)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush)
        ) {
            BackgroundGlow()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(1f))

                WelcomeIllustration()

                Spacer(Modifier.height(40.dp))

                Text(
                    text = App.INSTANCE.getString(R.string.app_name),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = Color.White
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = App.INSTANCE.getString(R.string.welcome_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.72f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(Modifier.weight(1f))

                FeaturesRow()

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF3D5AFE)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(
                        text = App.INSTANCE.getString(R.string.get_started),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun BackgroundGlow() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFD700).copy(alpha = 0.18f),
                    Color.Transparent
                ),
                center = Offset(size.width * 0.80f, size.height * 0.18f),
                radius = size.width * 0.55f
            ),
            radius = size.width * 0.55f,
            center = Offset(size.width * 0.80f, size.height * 0.18f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF7B2FBE).copy(alpha = 0.25f),
                    Color.Transparent
                ),
                center = Offset(size.width * 0.15f, size.height * 0.85f),
                radius = size.width * 0.45f
            ),
            radius = size.width * 0.45f,
            center = Offset(size.width * 0.15f, size.height * 0.85f)
        )
    }
}

@Composable
private fun WelcomeIllustration() {
    var visible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.75f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "illustrationScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600),
        label = "illustrationAlpha"
    )
    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .size(200.dp)
            .scale(scale)
            .alpha(alpha)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFD700).copy(alpha = 0.35f),
                        Color.Transparent
                    )
                ),
                radius = w * 0.38f,
                center = Offset(w * 0.68f, h * 0.32f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFFF59D), Color(0xFFFFB300)),
                    center = Offset(w * 0.61f, h * 0.26f),
                    radius = w * 0.20f
                ),
                radius = w * 0.20f,
                center = Offset(w * 0.68f, h * 0.32f)
            )

            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(0xFFD0DEFF)),
                    start = Offset(0f, h * 0.4f),
                    end = Offset(w * 0.3f, h * 0.9f)
                ),
                topLeft = Offset(w * 0.05f, h * 0.52f),
                size = Size(w * 0.72f, h * 0.34f)
            )
            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(0xFFD8E6FF)),
                    start = Offset(0f, h * 0.3f),
                    end = Offset(0f, h * 0.6f)
                ),
                topLeft = Offset(w * 0.10f, h * 0.38f),
                size = Size(w * 0.30f, h * 0.28f)
            )
            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(0xFFE0EAFF)),
                    start = Offset(0f, h * 0.22f),
                    end = Offset(0f, h * 0.55f)
                ),
                topLeft = Offset(w * 0.28f, h * 0.28f),
                size = Size(w * 0.34f, h * 0.32f)
            )
            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(0xFFD8E6FF)),
                    start = Offset(0f, h * 0.35f),
                    end = Offset(0f, h * 0.62f)
                ),
                topLeft = Offset(w * 0.46f, h * 0.40f),
                size = Size(w * 0.28f, h * 0.26f)
            )

            drawOval(
                color = Color.Black.copy(alpha = 0.08f),
                topLeft = Offset(w * 0.12f, h * 0.80f),
                size = Size(w * 0.65f, h * 0.08f)
            )
        }
    }
}

private data class FeatureItem(val icon: ImageVector, val label: String)

@Composable
private fun FeaturesRow() {
    val features = listOf(
        FeatureItem(
            icon = Icons.Outlined.WaterDrop,
            label = App.INSTANCE.getString(R.string.welcome_feature_precipitation)
        ),
        FeatureItem(
            icon = Icons.Outlined.Air,
            label = App.INSTANCE.getString(R.string.welcome_feature_wind)
        ),
        FeatureItem(
            icon = Icons.Outlined.Thermostat,
            label = App.INSTANCE.getString(R.string.welcome_feature_temperature)
        )
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        features.forEach { feature ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = feature.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = feature.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.65f)
                )
            }
        }
    }
}
