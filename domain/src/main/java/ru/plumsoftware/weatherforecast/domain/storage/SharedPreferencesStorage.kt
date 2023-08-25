package ru.plumsoftware.weatherforecast.domain.storage

import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsWindUnitsUseCase

class SharedPreferencesStorage
    (
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val getUserSettingsShowTipsUseCase: GetUserSettingsShowTipsUseCase,

    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
    private val saveUserSettingsAppThemeUseCase: SaveUserSettingsAppThemeUseCase,
    private val saveUserSettingsShowTipsUseCase: SaveUserSettingsShowTipsUseCase,

    private val saveUserSettingsWeatherUnitsUseCase: SaveUserSettingsWeatherUnitsUseCase,
    private val saveUserSettingsWindUnitsUseCase: SaveUserSettingsWindUnitsUseCase
) {
    fun get(): UserSettings {
        val execute = getUserSettingsUseCase.execute()
        return execute
    }

    fun getShowTips(): Boolean {
        val execute = getUserSettingsShowTipsUseCase.execute()
        return execute
    }

    fun save(userSettings: UserSettings) {
        saveUserSettingsUseCase.execute(userSettings = userSettings)
    }

    fun saveAppTheme(applicationTheme: Boolean) {
        saveUserSettingsAppThemeUseCase.execute(appTheme = applicationTheme)
    }

    fun saveShowTips(showTips: Boolean) {
        saveUserSettingsShowTipsUseCase.execute(showTips = showTips)
    }

    fun saveWeatherUnits(weatherUnits: WeatherUnits) {
        saveUserSettingsWeatherUnitsUseCase.execute(weatherUnits = weatherUnits)
    }

    fun saveWindUnits(windSpeed: WindSpeed) {
        saveUserSettingsWindUnitsUseCase.execute(value = windSpeed)
    }
}