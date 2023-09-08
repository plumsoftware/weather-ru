package ru.plumsoftware.weatherforecastru.material.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues

@Composable
fun TopBar(textResId: Int, onBackClick: () -> Unit) {
    with(ExtensionPaddingValues) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    bottom = _34dp,
                    top = _14dp
                )
                .fillMaxWidth()
        ) {
            BackArrowButton(
                modifier = Modifier,
                onClick = onBackClick,
                enabled = true
            )
            Text(
                text = App.INSTANCE.getString(textResId),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = _10dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            BackArrowButton(
                modifier = Modifier
                    .background(Color.Transparent),
                onClick = {},
                enabled = false
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
        }
    }
}