package ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.presentation.settings.presentation.SettingsSection
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.bold
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import java.util.Calendar

private const val CARD_STAGGER_MS = 60
private const val CARD_ENTER_MS = 320
private const val HERO_PULSE_MS = 300
private const val HERO_ICON_SIZE = 96
private const val HERO_ICON_RADIUS = 28

@Composable
fun AboutHeroSection(
    appName: String,
    version: String,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val heroHeight = (configuration.screenHeightDp * 0.35f).dp
    val iconScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        iconScale.animateTo(1.05f, animationSpec = tween(HERO_PULSE_MS, easing = FastOutSlowInEasing))
        iconScale.animateTo(1f, animationSpec = tween(HERO_PULSE_MS, easing = FastOutSlowInEasing))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heroHeight)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                        MaterialTheme.colorScheme.background,
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.about_app_icon),
                modifier = Modifier
                    .scale(iconScale.value)
                    .size(HERO_ICON_SIZE.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(HERO_ICON_RADIUS.dp),
                        spotColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.18f),
                        ambientColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.10f),
                    )
                    .clip(RoundedCornerShape(HERO_ICON_RADIUS.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = appName,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp).bold(),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.about_tagline),
                style = MaterialTheme.typography.bodyMedium.regular(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(
                    Dimens.cardBorder,
                    MaterialTheme.colorScheme.outlineVariant,
                ),
            ) {
                Text(
                    text = stringResource(R.string.about_version_format, version),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp).regular(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                )
            }
        }
    }
}

@Composable
fun AboutStaggeredCard(
    index: Int,
    content: @Composable ColumnScope.() -> Unit,
) {
    val visible = remember { androidx.compose.runtime.mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(index * CARD_STAGGER_MS.toLong())
        visible.value = true
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = fadeIn(
            animationSpec = tween(CARD_ENTER_MS, easing = FastOutSlowInEasing),
        ) + slideInVertically(
            animationSpec = tween(CARD_ENTER_MS, easing = FastOutSlowInEasing),
            initialOffsetY = { it / 5 },
        ),
    ) {
        Column(content = content)
    }
}

@Composable
fun AboutInfoRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    showChevron: Boolean = false,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val rowModifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                }
            }
    } else {
        Modifier.fillMaxWidth()
    }

    Row(
        modifier = rowModifier.padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp).regular(),
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp).regular(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        if (showChevron) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
fun AboutInfoDivider() {
    Divider(
        modifier = Modifier.padding(start = 42.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = Dimens.cardBorder,
    )
}

@Composable
fun AboutDataServicesCard() {
    SettingsSection(title = stringResource(R.string.about_section_data_services)) {
        AboutInfoRow(
            icon = Icons.Outlined.Cloud,
            title = stringResource(R.string.about_openweathermap_title),
            subtitle = stringResource(R.string.about_openweathermap_subtitle),
        )
        AboutInfoDivider()
        AboutInfoRow(
            icon = Icons.Outlined.Map,
            title = stringResource(R.string.about_osm_title),
            subtitle = stringResource(R.string.about_osm_subtitle),
        )
        AboutInfoDivider()
        AboutInfoRow(
            icon = Icons.Outlined.Palette,
            title = stringResource(R.string.about_meteocons_title),
            subtitle = stringResource(R.string.about_meteocons_subtitle),
        )
    }
}

@Composable
fun AboutContactsCard(
    onContactDeveloper: () -> Unit,
    onRateApp: () -> Unit,
    onReportBug: () -> Unit,
) {
    SettingsSection(title = stringResource(R.string.about_section_contacts)) {
        AboutInfoRow(
            icon = Icons.Outlined.Email,
            title = stringResource(R.string.about_contact_developer),
            showChevron = true,
            contentDescription = stringResource(R.string.about_open_email),
            onClick = onContactDeveloper,
        )
        AboutInfoDivider()
        AboutInfoRow(
            icon = Icons.Outlined.Star,
            title = stringResource(R.string.about_rate_app),
            showChevron = true,
            contentDescription = stringResource(R.string.about_open_store),
            onClick = onRateApp,
        )
        AboutInfoDivider()
        AboutInfoRow(
            icon = Icons.Outlined.BugReport,
            title = stringResource(R.string.about_report_bug),
            showChevron = true,
            contentDescription = stringResource(R.string.about_open_bug_report),
            onClick = onReportBug,
        )
    }
}

@Composable
fun AboutFooter(modifier: Modifier = Modifier) {
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val developerName = stringResource(R.string.developer_name)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(R.string.about_made_with_love),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp).regular(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.about_copyright, year, developerName),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp).regular(),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
            textAlign = TextAlign.Center,
        )
    }
}
