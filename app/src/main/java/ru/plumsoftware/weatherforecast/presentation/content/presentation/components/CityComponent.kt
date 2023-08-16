package ru.plumsoftware.weatherforecast.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore

@Composable
fun CityComponent(location: Location, dropDownMenuExpanded: Boolean, onCLickMoreVert: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = ExtensionPaddingValues._14dp,
                end = ExtensionPaddingValues._14dp,
                start = ExtensionPaddingValues._14dp
            )
    ) {
        with(MaterialTheme) {
            IconButton(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(shape = shapes.extraLarge),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                enabled = false,
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier.size(size = ExtensionSize.IconSize._20dp),
                    imageVector = Icons.Rounded.MoreVert,
                    tint = Color.Transparent,
                    contentDescription = ""
                )
            }
            Text(
                text = with(location) { "$city, $country" },
                style = typography.titleMedium,
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                textAlign = TextAlign.Center
            )
            Box {
                IconButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(shape = shapes.extraLarge),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = onCLickMoreVert
                ) {
                    Icon(
                        modifier = Modifier.size(size = ExtensionSize.IconSize._20dp),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = App.INSTANCE.getString(R.string.more_icon_dewscription)
                    )
                }

                DropdownMenu(
                    expanded = dropDownMenuExpanded,
                    onDismissRequest = { /*TODO*/ }
                ) {

                }
            }
        }
    }
}