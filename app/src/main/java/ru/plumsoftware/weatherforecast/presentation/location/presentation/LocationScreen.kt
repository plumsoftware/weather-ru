package ru.plumsoftware.weatherforecast.presentation.location.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.location.presentation.components.BackArrowButton
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecast.presentation.location.viewmodel.LocationViewModel

@Composable
fun LocationScreen(locationViewModel: LocationViewModel) {
    val state by locationViewModel.state.collectAsState()

    LaunchedEffect(locationViewModel) {
        locationViewModel.label.collect { label ->
            when (label) {
                LocationStore.Label.AuthorizationSuccess -> TODO()
                is LocationStore.Label.ConfirmLocation -> {
                    locationViewModel.onOutput(LocationViewModel.Output.OpenContentScreen(city = label.value))
                }

                LocationStore.Label.BackButtonClicked -> {
                    locationViewModel.onOutput(LocationViewModel.Output.OpenAuthorizationScreen)
                }
            }
        }
    }
    LocationScreen(
        event = locationViewModel::onEvent,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationScreen(
    event: (LocationStore.Intent) -> Unit,
    state: LocationStore.State
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = ExtensionPaddingValues._24dp)
    ) {
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
                    onClick = {
                        event(LocationStore.Intent.BackButtonClicked)
                    })
                Text(
                    text = "Местоположение", // TODO: Replace with string resources
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
                    onClick = {

                    })
                Spacer(modifier = Modifier.width(width = 8.dp))
            }
            Spacer(modifier = Modifier.height(height = _10dp))
            OutlinedTextField(
                value = state.city,
                textStyle = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .focusRequester(state.focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            if (state.city.isNotEmpty())
                                event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = true))
                        }
                    },
                onValueChange = {
                    with(it) {
                        event(LocationStore.Intent.TextChange(text = this@with))
                        event(LocationStore.Intent.TextError(isSyntaxError = false))
                        event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = this@with.isNotEmpty()))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        event(LocationStore.Intent.TextError(isSyntaxError = state.city.isEmpty()))
                        if (state.city.isNotEmpty()) {
                            event(LocationStore.Intent.SearchButtonClicked(city = state.city))
                        }
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                    focusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                ),
                shape = RoundedCornerShape(100.dp),
                isError = state.isSyntaxError,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search", //TODO: replace with string resources
                        tint = if (state.isSyntaxError) MaterialTheme.colorScheme.error else LocalContentColor.current
                    )
                },
                placeholder = {
                    Text(
                        text = "Введите город", //TODO: replace with string resources
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary),
                    )
                },
                trailingIcon = {
                    if (state.isVisibleCloseIcon) {
                        IconButton(onClick = {
                            event(LocationStore.Intent.TextChange(text = ""))
                            event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = false))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "clear text field", //TODO: replace with string resources
                            )
                        }
                    }
                }
            )
        }

        LaunchedEffect(Unit) {
            state.focusRequester.requestFocus()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        {

        }
    }
}