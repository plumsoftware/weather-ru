package ru.plumsoftware.weatherforecast.presentation.content.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@Composable
fun BoxScope.ContentBackgroundLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = .45f)
            .clip(
                shape = RoundedCornerShape(
                    bottomEnd = ExtensionSize.Corners._24dp,
                    bottomStart = ExtensionSize.Corners._24dp
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(color = 0xFFED3B9B),
                        Color(color = 0xFFA8C0FF),
                        Color(color = 0xFFC071FF)
                    )
                )
            )
            .align(alignment = Alignment.TopCenter)
    ) {

    }
}