package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunny
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.widget.utilites.darkerColor
import ru.plumsoftware.weatherforecastru.widget.utilites.makeColorDarker

@Composable
fun WidgetPreview(corners: Int, background: Color) {

    Box(
        modifier = Modifier
            .size(width = ExtensionSize.Widgets.width_1, height = ExtensionSize.Widgets.height_1)
            .clip(shape = RoundedCornerShape(corners.dp))
            .background(color = background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(all = ExtensionPaddingValues._8dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = PlumsoftwareIconPack.Weather.Sunny,
                contentDescription = null,
                modifier = Modifier
                    .size(size = ExtensionSize.IconSize._64dp),
                colorFilter = ColorFilter.tint(darkerColor(color = background)),
            )
            Spacer(modifier = Modifier.width(width = ExtensionPaddingValues._10dp))
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${15}°",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = darkerColor(color = background),
                        fontSize = 32.sp
                    )
                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${10}°",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = makeColorDarker(
                                color = background
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(width = ExtensionPaddingValues._4dp))
                    Text(
                        text = "${18}°",
                        style = MaterialTheme.typography.bodyMedium.copy(color = darkerColor(color = background))
                    )
                }
            }
        }
    }
}