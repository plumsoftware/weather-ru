package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.statusBarTopPadding(): Modifier = windowInsetsPadding(
    WindowInsets.statusBars.only(WindowInsetsSides.Top),
)

@Composable
fun navigationBarBottomInset(): Dp =
    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

@Composable
fun NavigationBarSpacer() {
    Spacer(modifier = Modifier.height(navigationBarBottomInset()))
}

@Composable
fun contentBottomPadding(extra: Dp = 16.dp): PaddingValues =
    PaddingValues(bottom = navigationBarBottomInset() + extra)
