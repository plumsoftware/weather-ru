package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.bold
import ru.plumsoftware.weatherforecastru.presentation.ui.medium
import ru.plumsoftware.weatherforecastru.presentation.ui.regular

private data class AlertSeverityColors(
    val background: Color,
    val border: Color,
    val chipBackground: Color,
    val chipText: Color,
)

@Composable
private fun alertSeverityColors(level: AlertSeverityLevel): AlertSeverityColors {
    val isDark = isSystemInDarkTheme()
    return when (level) {
        AlertSeverityLevel.CRITICAL -> AlertSeverityColors(
            background = if (isDark) Color(0xFF3B1518) else Color(0xFFFFEBEE),
            border = Color(0xFFD32F2F),
            chipBackground = Color(0xFFD32F2F),
            chipText = Color.White,
        )
        AlertSeverityLevel.HIGH -> AlertSeverityColors(
            background = if (isDark) Color(0xFF3B2210) else Color(0xFFFFF3E0),
            border = Color(0xFFE65100),
            chipBackground = Color(0xFFE65100),
            chipText = Color.White,
        )
        AlertSeverityLevel.MEDIUM -> AlertSeverityColors(
            background = if (isDark) Color(0xFF3B3610) else Color(0xFFFFFDE7),
            border = Color(0xFFF9A825),
            chipBackground = Color(0xFFF9A825),
            chipText = Color(0xFF1A1E2E),
        )
        AlertSeverityLevel.LOW -> AlertSeverityColors(
            background = if (isDark) Color(0xFF102033) else Color(0xFFE3F2FD),
            border = Color(0xFF1565C0),
            chipBackground = Color(0xFF1565C0),
            chipText = Color.White,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherAlertsSection(
    alerts: List<WeatherAlert>,
    modifier: Modifier = Modifier,
) {
    if (alerts.isEmpty()) return

    val highestSeverity = alerts.maxByOrNull { it.severityLevel.rank }?.severityLevel
        ?: AlertSeverityLevel.LOW
    val highestColors = alertSeverityColors(highestSeverity)
    val shouldShake = highestSeverity == AlertSeverityLevel.CRITICAL ||
        highestSeverity == AlertSeverityLevel.HIGH
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(alerts) {
        if (shouldShake) {
            delay(300)
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    0f at 0
                    8f at 80
                    -8f at 160
                    4f at 240
                    -4f at 320
                    0f at 400
                },
            )
        }
    }

    val pagerState = rememberPagerState(initialPage = 0) { alerts.size }
    val currentAlert = alerts.getOrNull(pagerState.currentPage)
    val currentColors = alertSeverityColors(currentAlert?.severityLevel ?: AlertSeverityLevel.LOW)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .offset(x = shakeOffset.value.dp)
            .padding(horizontal = Dimens.screenPaddingH),
    ) {
        AlertsSectionHeader(
            iconColor = highestColors.border,
            pulseIcon = shouldShake,
            currentPage = pagerState.currentPage + 1,
            totalPages = alerts.size,
        )
        Spacer(Modifier.height(6.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
        ) { page ->
            WeatherAlertCard(alert = alerts[page])
        }
        if (alerts.size > 1) {
            Spacer(Modifier.height(8.dp))
            AlertsPagerIndicator(
                pageCount = alerts.size,
                currentPage = pagerState.currentPage,
                activeColor = currentColors.border,
            )
        }
    }
}

@Composable
private fun AlertsSectionHeader(
    iconColor: Color,
    pulseIcon: Boolean,
    currentPage: Int,
    totalPages: Int,
) {
    val iconAlpha = if (pulseIcon) {
        val transition = rememberInfiniteTransition(label = "alertIconPulse")
        transition.animateFloat(
            initialValue = 1f,
            targetValue = 0.4f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "alertIconPulseAlpha",
        ).value
    } else {
        1f
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Outlined.WarningAmber,
                contentDescription = null,
                modifier = Modifier
                    .size(14.dp)
                    .alpha(iconAlpha),
                tint = iconColor,
            )
            Text(
                text = stringResource(R.string.alerts_section_header),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.08.sp).medium(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (totalPages > 1) {
            Text(
                text = stringResource(R.string.alerts_page_indicator, currentPage, totalPages),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp).regular(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun WeatherAlertCard(alert: WeatherAlert) {
    val colors = alertSeverityColors(alert.severityLevel)
    var expanded by remember(alert.headline, alert.expires) { mutableStateOf(false) }
    val expiresLabel = formatAlertExpires(alert.expires, alert.expiresAtMillis)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(Dimens.cardRadius),
        color = colors.background,
        border = androidx.compose.foundation.BorderStroke(
            Dimens.cardBorder,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .heightIn(min = 120.dp)
                    .background(colors.border),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(Dimens.cardPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    AlertSeverityChip(
                        level = alert.severityLevel,
                        colors = colors,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    if (expiresLabel != null) {
                        Text(
                            text = "${stringResource(R.string.alerts_expires_prefix)} $expiresLabel",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp).regular(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Text(
                    text = alert.headline,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp).bold(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (alert.description.isNotBlank()) {
                    Text(
                        text = alert.description,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp).regular(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (alert.description.length > 120 || alert.description.count { it == '\n' } >= 2) {
                        TextButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.padding(top = 0.dp),
                        ) {
                            Text(
                                text = if (expanded) {
                                    "${stringResource(R.string.alerts_collapse)} ▴"
                                } else {
                                    "${stringResource(R.string.alerts_expand)} ▾"
                                },
                                style = MaterialTheme.typography.labelMedium.regular(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertSeverityChip(
    level: AlertSeverityLevel,
    colors: AlertSeverityColors,
    modifier: Modifier = Modifier,
) {
    val label = when (level) {
        AlertSeverityLevel.CRITICAL -> stringResource(R.string.alerts_severity_critical)
        AlertSeverityLevel.HIGH -> stringResource(R.string.alerts_severity_high)
        AlertSeverityLevel.MEDIUM -> stringResource(R.string.alerts_severity_medium)
        AlertSeverityLevel.LOW -> stringResource(R.string.alerts_severity_low)
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = colors.chipBackground,
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp).bold(),
            color = colors.chipText,
        )
    }
}

@Composable
private fun AlertsPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    activeColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(width = 6.dp, height = 6.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) {
                            activeColor
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
                        },
                    ),
            )
        }
    }
}
