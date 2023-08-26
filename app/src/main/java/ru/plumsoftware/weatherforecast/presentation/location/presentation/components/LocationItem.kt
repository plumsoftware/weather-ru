package ru.plumsoftware.weatherforecast.presentation.location.presentation.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationItem(city: String, country: String, onClick: () -> Unit) {
    with(MaterialTheme) {
        Card(
            onClick = onClick,
            shape = shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = ExtensionPaddingValues._10dp)
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
                    .padding(paddingValues = PaddingValues(vertical = ExtensionPaddingValues._10dp))
            ) {
                Image(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = App.INSTANCE.getString(R.string.location_icon_description),
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .padding(start = ExtensionPaddingValues._12dp)
                        .size(size = ExtensionSize.IconSize._24dp),
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