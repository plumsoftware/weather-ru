package ru.plumsoftware.weatherforecast.presentation.location.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@Composable
internal fun LocationItem(city: String, country: String, onClick: () -> Unit) {
    with(MaterialTheme) {
        Button(
            onClick = onClick,
            shape = shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues = PaddingValues(all = ExtensionPaddingValues._16dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    ExtensionPaddingValues._10dp,
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = "image description", //TODO: Replace with string resources
                    contentScale = ContentScale.None,
                    modifier = Modifier.size(size = ExtensionSize.IconSize._24dp),
                    colorFilter = ColorFilter.tint(colorScheme.primary)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        ExtensionPaddingValues._10dp,
                        Alignment.Top
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(text = city, style = typography.labelMedium)
                    Text(
                        text = country,
                        style = typography.bodyMedium.copy(color = colorScheme.secondary)
                    )
                }
            }
        }
    }
}