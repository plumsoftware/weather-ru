package ru.plumsoftware.weatherforecastru.domain.storage

import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.widget.SaveWidgetConfigUseCase

class SharedPreferencesStorage
    (
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val getUserSettingsShowTipsUseCase: GetUserSettingsShowTipsUseCase,
    private val getFirstUseCase: GetFirstUseCase,

    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
    private val saveUserSettingsAppThemeUseCase: SaveUserSettingsAppThemeUseCase,
    private val saveUserSettingsShowTipsUseCase: SaveUserSettingsShowTipsUseCase,
    private val saveUserSettingsLocationUseCase: SaveUserSettingsLocationUseCase,
    private val saveFirstUseCase: SaveFirstUseCase,

    private val saveUserSettingsWeatherUnitsUseCase: SaveUserSettingsWeatherUnitsUseCase,
    private val saveUserSettingsWindUnitsUseCase: SaveUserSettingsWindUnitsUseCase,

    private val saveWidgetConfigUseCase: SaveWidgetConfigUseCase,
    private val getWidgetConfigUseCase: GetWidgetConfigUseCase
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

    fun saveFirst(first: Boolean) {
        saveFirstUseCase.execute(value = first)
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

    fun saveLocation(location: Location) {
        saveUserSettingsLocationUseCase.execute(value = location)
    }

    fun saveWidget(widgetConfig: WidgetConfig) {
        saveWidgetConfigUseCase.execute(widgetConfig = widgetConfig)
    }

    fun getWidget(): WidgetConfig {
        val widgetConfig = getWidgetConfigUseCase.execute()
        return widgetConfig
    }

    fun getFirst() : Boolean {
        val first = getFirstUseCase.execute()
        return first
    }
}