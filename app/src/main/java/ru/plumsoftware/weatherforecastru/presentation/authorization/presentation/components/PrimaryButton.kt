package ru.plumsoftware.weatherforecastru.presentation.authorization.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .then(other = modifier),
        colors = ButtonDefaults.buttonColors(),
        contentPadding = PaddingValues(
            horizontal = ExtensionPaddingValues._24dp,
            vertical = ExtensionPaddingValues._16dp
        ),
        content = content
    )
}