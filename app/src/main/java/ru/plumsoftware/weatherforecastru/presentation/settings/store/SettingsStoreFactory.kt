package ru.plumsoftware.weatherforecastru.presentation.settings.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.domain.constants.Constants
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage

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
        data class WeatherUnit(val value: WeatherUnits) : Msg
        data class WindUnit(val value: WindSpeed) : Msg
    }

    private object ReducerImpl : Reducer<SettingsStore.State, SettingsStoreFactory.Msg> {

        override fun SettingsStore.State.reduce(msg: SettingsStoreFactory.Msg): SettingsStore.State =
            when (msg) {
                is Msg.CheckBoxValue -> copy(
                    checkBoxValue = msg.value
                )

                is Msg.WeatherUnit -> copy(
                    weatherUnit = msg.value
                )

                is Msg.WindUnit -> copy(
                    windSpeed = msg.value
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
                SettingsStore.Intent.ChangeWeatherUnits -> {
                    val weatherUnits = WeatherUnits(
                        unitsPresentation = if (getState().weatherUnit.unitsPresentation == Constants.Settings.METRIC.second)
                            Constants.Settings.IMPERIAL.second else Constants.Settings.METRIC.second,
                        unitsValue = if (getState().weatherUnit.unitsValue == Constants.Settings.METRIC.first)
                            Constants.Settings.IMPERIAL.first else Constants.Settings.METRIC.first
                    )

                    dispatch(Msg.WeatherUnit(value = weatherUnits))
                    saveWeatherUnits(
                        sharedPreferencesStorage = sharedPreferencesStorage,
                        value = weatherUnits
                    )
                    publish(SettingsStore.Label.SettingsChange)
                }

                SettingsStore.Intent.ChangeWindUnits -> {

                    val windPresentation =
                        if (getState().windSpeed.windPresentation == Constants.Settings.M_S.first)
                            Constants.Settings.MI_H.first else Constants.Settings.M_S.first

                    val windValue =
                        if (getState().windSpeed.windValue == Constants.Settings.M_S.second)
                            Constants.Settings.MI_H.second else Constants.Settings.M_S.second

                    val windSpeed = WindSpeed(
                        windPresentation = windPresentation,
                        windValue = windValue
                    )

                    dispatch(Msg.WindUnit(value = windSpeed))
                    saveWindUnits(
                        sharedPreferencesStorage = sharedPreferencesStorage,
                        value = windSpeed
                    )
                    publish(SettingsStore.Label.SettingsChange)
                }

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
                SettingsStore.Intent.WidgetConfigureSettings -> publish(SettingsStore.Label.WidgetConfigureSettings)
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
//                    region::Weather units
                    dispatch(
                        Msg.WeatherUnit(
                            value = WeatherUnits(
                                unitsPresentation = weatherUnits.unitsPresentation,
                                unitsValue = weatherUnits.unitsValue
                            )
                        )
                    )
                    dispatch(
                        Msg.WindUnit(
                            value = WindSpeed(
                                windPresentation = windSpeed.windPresentation,
                                windValue = windSpeed.windValue
                            )
                        )
                    )
//                    endregion
                }
            }
        }

        private fun saveTheme(sharedPreferencesStorage: SharedPreferencesStorage, value: Boolean) {
            sharedPreferencesStorage.saveAppTheme(applicationTheme = value)
        }

        private fun saveWeatherUnits(
            sharedPreferencesStorage: SharedPreferencesStorage,
            value: WeatherUnits
        ) {
            sharedPreferencesStorage.saveWeatherUnits(weatherUnits = value)
        }

        private fun saveWindUnits(
            sharedPreferencesStorage: SharedPreferencesStorage,
            value: WindSpeed
        ) {
            sharedPreferencesStorage.saveWindUnits(windSpeed = value)
        }
    }
}