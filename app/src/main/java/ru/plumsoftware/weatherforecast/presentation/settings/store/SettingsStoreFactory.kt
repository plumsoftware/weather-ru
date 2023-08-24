package ru.plumsoftware.weatherforecast.presentation.settings.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage

class SettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SettingsStore =
        object : SettingsStore,
            Store<SettingsStore.Intent, SettingsStore.State, SettingsStore.Label> by storeFactory.create(
                name = "Location",
                initialState = SettingsStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(SettingsStoreFactory.Action.InitSettings)
                    }
                },
                reducer = SettingsStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitSettings : Action
    }

    sealed interface Msg {
        data class CheckBoxValue(val value: Boolean) : Msg
    }

    private object ReducerImpl : Reducer<SettingsStore.State, SettingsStoreFactory.Msg> {

        override fun SettingsStore.State.reduce(msg: SettingsStoreFactory.Msg): SettingsStore.State =
            when (msg) {
                is Msg.CheckBoxValue -> copy(
                    checkBoxValue = msg.value
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SettingsStore.Intent, SettingsStoreFactory.Action, SettingsStore.State, SettingsStoreFactory.Msg, SettingsStore.Label>() {

        override fun executeIntent(
            intent: SettingsStore.Intent,
            getState: () -> SettingsStore.State
        ) =
            when (intent) {
                SettingsStore.Intent.BackButtonClicked -> publish(SettingsStore.Label.BackButtonClicked)
                SettingsStore.Intent.AboutApp -> publish(SettingsStore.Label.AboutApp)
                SettingsStore.Intent.ChangeWeatherUnits -> publish(SettingsStore.Label.ChangeWeatherUnits)
                SettingsStore.Intent.ChangeWindUnits -> publish(SettingsStore.Label.ChangeWindUnits)
                is SettingsStore.Intent.CheckBoxValue -> {
                    dispatch(Msg.CheckBoxValue(value = intent.value))
                    saveTheme(
                        sharedPreferencesStorage = sharedPreferencesStorage,
                        value = intent.value
                    )
                    publish(SettingsStore.Label.ChangeTheme(value = intent.value))
                }

                SettingsStore.Intent.LeaveFeedBack -> publish(SettingsStore.Label.LeaveFeedBack)
                SettingsStore.Intent.Share -> publish(SettingsStore.Label.Share)
            }

        override fun executeAction(
            action: SettingsStoreFactory.Action,
            getState: () -> SettingsStore.State
        ) =
            when (action) {
                Action.InitSettings -> initSettings(sharedPreferencesStorage = sharedPreferencesStorage)
            }

        private fun initSettings(sharedPreferencesStorage: SharedPreferencesStorage) {
            scope.launch {
                with(sharedPreferencesStorage.get()) {
                    dispatch(Msg.CheckBoxValue(value = isDarkTheme))
                }
            }
        }

        private fun saveTheme(sharedPreferencesStorage: SharedPreferencesStorage, value: Boolean) {
            sharedPreferencesStorage.save(applicationTheme = value)
        }
    }
}