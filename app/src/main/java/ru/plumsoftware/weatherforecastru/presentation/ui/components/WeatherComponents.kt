package ru.plumsoftware.weatherforecastru.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.annotation.DrawableRes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.weatherforecastru.presentation.ui.medium
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiGoodDark
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiGoodLight
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiModerateDark
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiModerateLight
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiUnhealthyDark
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiUnhealthyLight
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiVeryUnhealthyDark
import ru.plumsoftware.weatherforecastru.presentation.ui.AqiVeryUnhealthyLight
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.statusBarTopPadding

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val mod = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    Surface(
        modifier = mod,
        shape = RoundedCornerShape(Dimens.cardRadius),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(Dimens.cardBorder, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.cardPadding),
            content = content,
        )
    }
}

@Composable
fun DetailCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    @DrawableRes iconRes: Int? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    valueColor: Color? = null,
    onClick: (() -> Unit)? = null,
    isClickable: Boolean = false,
) {
    WeatherCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                when {
                    iconRes != null -> {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.detailMetricIconSize),
                            contentScale = ContentScale.Fit,
                        )
                    }
                    icon != null -> {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.detailMetricIconSize),
                            tint = iconTint,
                        )
                    }
                }
                Text(
                    value,
                    style = MaterialTheme.typography.titleMedium,
                    color = valueColor ?: MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (isClickable) {
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(14.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    icon: ImageVector? = Icons.Outlined.Schedule,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    titleFontWeight: FontWeight? = null,
) {
    val titleStyle = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.08.sp).let { base ->
        when (titleFontWeight) {
            FontWeight.Medium -> base.medium()
            FontWeight.Normal, null -> base
            else -> base.copy(fontWeight = titleFontWeight)
        }
    }
    Row(
        modifier = modifier.fillMaxWidth().padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = iconTint,
            )
        }
        Text(
            title.uppercase(),
            style = titleStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun aqiColor(aqi: Int): Color = when {
    aqi <= 50 -> if (isSystemInDarkTheme()) AqiGoodDark else AqiGoodLight
    aqi <= 100 -> if (isSystemInDarkTheme()) AqiModerateDark else AqiModerateLight
    aqi <= 150 -> if (isSystemInDarkTheme()) AqiUnhealthyDark else AqiUnhealthyLight
    else -> if (isSystemInDarkTheme()) AqiVeryUnhealthyDark else AqiVeryUnhealthyLight
}

@Composable
fun pollutantBarColor(ratio: Float): Color = when {
    ratio < 0.5f -> aqiColor(30)
    ratio < 0.75f -> aqiColor(70)
    ratio < 1f -> aqiColor(120)
    else -> aqiColor(180)
}

@Composable
fun AqiScale(currentAqi: Int) {
    val segments = listOf(
        Color(0xFF27A85A) to "Хор.",
        Color(0xFFF5A623) to "Умер.",
        Color(0xFFE85B3D) to "Вред.",
        Color(0xFF9B27AF) to "Оч.вр.",
    )
    val ranges = listOf(0..50, 51..100, 101..150, 151..300)
    val currentSegment = ranges.indexOfFirst { currentAqi in it }.coerceAtLeast(0)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
        ) {
            segments.forEach { (color, _) ->
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(color))
            }
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            segments.forEachIndexed { i, (color, label) ->
                Text(
                    label,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (i == currentSegment) color
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    fontWeight = if (i == currentSegment) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
fun BackTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarTopPadding()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.material3.IconButton(onClick = onBack) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.medium(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Spacer(Modifier.width(48.dp))
    }
}

@Composable
fun SettingsDivider() {
    Divider(
        modifier = Modifier.padding(start = 58.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 0.5.dp,
    )
}

@Composable
fun SettingsIcon(icon: ImageVector, bgColor: Color) {
    val isDark = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .size(Dimens.iconBoxSize)
            .clip(RoundedCornerShape(Dimens.iconBoxRadius))
            .background(if (isDark) bgColor.copy(alpha = 0.28f) else bgColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun AqiHeroCard(aqi: Int, label: String, description: String) {
    val color = aqiColor(aqi)
    WeatherCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f))
                    .border(2.dp, color, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        aqi.toString(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 36.sp,
                        ),
                        color = color,
                    )
                    Text(
                        "AQI",
                        style = MaterialTheme.typography.labelSmall,
                        color = color.copy(alpha = 0.7f),
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(label, style = MaterialTheme.typography.titleMedium, color = color)
            Spacer(Modifier.height(4.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            AqiScale(currentAqi = aqi)
        }
    }
}
