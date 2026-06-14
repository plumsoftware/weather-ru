package ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.store.AboutAppStore
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.viewmodel.AboutAppViewModel
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.NavigationBarSpacer
import ru.plumsoftware.weatherforecastru.presentation.ui.contentBottomPadding

@Composable
fun AboutApp(aboutAppViewModel: AboutAppViewModel) {
    val state by aboutAppViewModel.state.collectAsState()

    LaunchedEffect(aboutAppViewModel) {
        aboutAppViewModel.label.collect { label ->
            when (label) {
                AboutAppStore.Label.BackButtonClicked -> {
                    aboutAppViewModel.onOutput(AboutAppViewModel.Output.OpenSettingsScreen)
                }
            }
        }
    }

    AboutApp(state = state, event = aboutAppViewModel::onEvent)
}

@Composable
private fun AboutApp(state: AboutAppStore.State, event: (AboutAppStore.Intent) -> Unit) {
    val context = LocalContext.current
    val appName = state.appName.ifBlank { stringResource(R.string.app_name) }
    val applicationId = context.packageName
    val bugSubject = stringResource(R.string.about_bug_report_subject)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = ExtensionPaddingValues._18dp),
        ) {
            TopBar(
                showBack = true,
                textResId = R.string.about_app,
                onBackClick = { event(AboutAppStore.Intent.BackButtonClicked) },
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentBottomPadding(extra = 32.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.sectionGap),
        ) {
            item {
                AboutHeroSection(
                    appName = appName,
                    version = state.version,
                )
            }
            item {
                AboutStaggeredCard(index = 0) {
                    Column(Modifier.padding(horizontal = Dimens.screenPaddingH)) {
                        AboutDataServicesCard()
                    }
                }
            }
            item {
                AboutStaggeredCard(index = 1) {
                    Column(Modifier.padding(horizontal = Dimens.screenPaddingH)) {
                        AboutContactsCard(
                        onContactDeveloper = {
                            AboutAppActions.openDeveloperEmail(context)
                        },
                        onRateApp = {
                            AboutAppActions.openRateApp(context, applicationId)
                        },
                        onReportBug = {
                            AboutAppActions.openBugReport(context, bugSubject, state.version)
                        },
                        )
                    }
                }
            }
            item {
                AboutStaggeredCard(index = 2) {
                    Column(Modifier.padding(horizontal = Dimens.screenPaddingH)) {
                        AboutFooter(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
            item {
                NavigationBarSpacer()
            }
        }
    }
}
