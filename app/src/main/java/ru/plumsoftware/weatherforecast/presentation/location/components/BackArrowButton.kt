package ru.plumsoftware.weatherforecast.presentation.location.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@Composable
fun BackArrowButton(modifier: Modifier, onClick: () -> Unit) {
    with(MaterialTheme) {
        IconButton(
            modifier = Modifier
                .size(size = ExtensionSize.IconSize._20dp)
                .clip(shape = shapes.extraLarge)
                .then(other = modifier),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Transparent
            ),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "arrow back" //TODO: replace with string resources
            )
        }
    }
}