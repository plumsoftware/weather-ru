package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize

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
    onCheckedChange: (Boolean) -> Unit,
    onClickOpenLocation: () -> Unit,
    onClickOpenSettings: () -> Unit,
    onCLickOpenAirQuality: () -> Unit
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._10dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = stringResource(id = R.string.location_icon_description)
                )
                Text(
                    text = with(location) { "$city ${if (country.isNotEmpty()) ", $country" else ""}" },
                    style = typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
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
                            text = stringResource(id = R.string.location),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = {
                        onClickOpenLocation()
                        onCloseDropDownMenu()
                    })


                    DropdownMenuItem(text = {
                        Text(
                            text = stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = {
                        onClickOpenSettings()
                        onCloseDropDownMenu()
                    })

                    DropdownMenuItem(text = {
                        Text(
                            text = stringResource(id = R.string.air_quality),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(horizontal = ExtensionPaddingValues._10dp),
                            textAlign = TextAlign.Start
                        )
                    }, onClick = {
                        onCloseDropDownMenu()
                        onCLickOpenAirQuality()
                    })

//                    Divider(modifier = Modifier.height(height = ExtensionSize.Divider.height))
//                    DropdownMenuItem(text = {
//                        Row(
//                            horizontalArrangement = Arrangement.Start,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Checkbox(
//                                checked = checkBoxValue,
//                                onCheckedChange = onCheckedChange
//                            )
//                            Spacer(modifier = Modifier.width(width = ExtensionPaddingValues._10dp))
//                            Text(
//                                text = stringResource(id = R.string.tips),
//                                style = MaterialTheme.typography.labelMedium,
//                                modifier = Modifier
//                                    .padding(end = ExtensionPaddingValues._10dp)
//                                    .fillMaxWidth(),
//                                textAlign = TextAlign.Start
//                            )
//                        }
//                    }, onClick = {
//                        onCheckedChange(!checkBoxValue)
//                        onCloseDropDownMenu()
//                    })
                }
            }
        }
    }
}