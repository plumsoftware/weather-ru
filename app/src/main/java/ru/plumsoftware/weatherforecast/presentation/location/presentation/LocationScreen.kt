package ru.plumsoftware.weatherforecast.presentation.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.location.components.BackArrowButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen() {
//    region::Variables
    val query = remember {
        mutableStateOf(value = "")
    }
    val closeIcon = remember {
        mutableStateOf(value = false)
    }
    val isError = remember {
        mutableStateOf(value = false)
    }
//    endregion

//    region::UI
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
                horizontalArrangement = Arrangement.Start,
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

                    })
                Text(
                    text = "Местоположение", // TODO: Replace with string resources
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = _10dp),
                    textAlign = TextAlign.Center
                )
                BackArrowButton(
                    modifier = Modifier
                        .background(Color.Transparent),
                    onClick = {

                    })
            }
            Spacer(modifier = Modifier.height(height = _10dp))
            OutlinedTextField(
                value = query.value,
                textStyle = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onValueChange = {
                    closeIcon.value = it.isNotEmpty()
                    query.value = it
                },
                keyboardOptions = KeyboardOptions.Default,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                    focusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                ),
                shape = RoundedCornerShape(100.dp),
                isError = isError.value,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search" //TODO: replace with string resources
                    )
                },
                placeholder = {
                    Text(
                        text = "Введите город", //TODO: replace with string resources
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary),
                    )
                },
                trailingIcon = {
                    if (closeIcon.value) {
                        IconButton(onClick = {
                            query.value = ""
                            closeIcon.value = false
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

        LazyColumn(modifier = Modifier.fillMaxWidth().wrapContentHeight())
        {

        }
    }
//    endregion
}