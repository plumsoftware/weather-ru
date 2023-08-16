package ru.plumsoftware.weatherforecast.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore

@Composable
fun CityComponent(
    location: Location,
//    region::DropDownMenu
    dropDownMenuExpanded: Boolean,
    onCloseDropDownMenu: () -> Unit,
//    endregion
    onCLickMoreVert: () -> Unit,
//    region::Check box
    checkBoxValue: Boolean,
    onCheckedChange: (Boolean) -> Unit
//    endregion
) {
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
                    onDismissRequest = onCloseDropDownMenu,
                    modifier = Modifier.clip(MaterialTheme.shapes.medium),
                ) {
                    DropdownMenuItem(text = {
                        Text(
                            text = "Местоположение",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = { /*TODO*/ })


                    DropdownMenuItem(text = {
                        Text(
                            text = "Настройки",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = { /*TODO*/ })

                    DropdownMenuItem(text = {
                        Text(
                            text = "Качество воздуха",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = { /*TODO*/ })

                    Divider(modifier = Modifier.height(height = ExtensionSize.Divider.height))
                    DropdownMenuItem(text = {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkBoxValue,
                                onCheckedChange = onCheckedChange
                            )
                            Spacer(modifier = Modifier.width(width = ExtensionPaddingValues._10dp))
                            Text(
                                text = "Показывать посказки",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier
                                    .padding(end = ExtensionPaddingValues._10dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }, onClick = { /*TODO*/ })
                }
            }
        }
    }
}