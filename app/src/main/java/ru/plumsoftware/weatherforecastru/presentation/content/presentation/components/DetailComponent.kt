package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.ui.md_theme_text_cover
import ru.plumsoftware.weatherforecastru.presentation.ui.md_theme_tint_cover

@Composable
fun RowScope.DetailComponent(title: String, description: String, pair: Pair<ImageVector, Color>) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .weight(weight = 1.0f)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._10dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = ExtensionPaddingValues._14dp)
        ) {
            if (title == "0") {
                CircularProgressIndicator()
            } else {
                Icon(
                    imageVector = pair.first,
                    contentDescription = "",
                    tint = pair.second.also { md_theme_tint_cover })
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._4dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(text = title, style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(color = md_theme_text_cover)
                    )
                }
            }
        }
    }
}