package ru.plumsoftware.weatherforecast.presentation.content.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@Composable
fun WeatherStatus() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(fraction = 1.0f)
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._24dp))
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = "weather status logo",
            tint = Color.White,
            modifier = Modifier.size(size = ExtensionSize.IconSize._64dp)
        )
        Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._24dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._10dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(all = ExtensionPaddingValues._10dp)
        ) {
            Text(
                text = "Солнечно",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
            )
            Text(
                text = "Пятница, 21 июля 2023",
                style = MaterialTheme.typography.labelMedium.copy(color = Color.White)
            )
        }
    }
}