package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.utilities.getOutfitSuggestion
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import java.time.LocalDateTime

@Composable
fun TipsComponent(code: Int) {
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
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._8dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = ExtensionPaddingValues._14dp)
        ) {
            if (code in 400..404)
                CircularProgressIndicator()
            else {
                Text(
                    text = stringResource(id = R.string.tips_rocket),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp)
                )
                Text(
                    text = getOutfitSuggestion(localDateTime = LocalDateTime.now()),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}