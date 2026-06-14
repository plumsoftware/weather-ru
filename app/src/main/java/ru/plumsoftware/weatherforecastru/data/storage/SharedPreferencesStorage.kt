package ru.plumsoftware.weatherforecastru.data.storage

import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig

class SharedPreferencesStorage
    (
    private val getUserSettingsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase,
    private val getUserSettingsShowTipsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsShowTipsUseCase,
    private val getFirstUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.GetFirstUseCase,

    private val saveUserSettingsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsUseCase,
    private val saveUserSettingsAppThemeUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsAppThemeUseCase,
    private val saveUserSettingsShowTipsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase,
    private val saveUserSettingsLocationUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase,
    private val saveFirstUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveFirstUseCase,

    private val saveUserSettingsWeatherUnitsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWeatherUnitsUseCase,
    private val saveUserSettingsWindUnitsUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWindUnitsUseCase,

    private val saveWidgetConfigUseCase: ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase,
    private val getWidgetConfigUseCase: ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase,

    private val getNotificationItemUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.GetNotificationItemUseCase,
    private val saveNotificationItemUseCase: ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveNotificationItemUseCase,
) {
    private var cachedSettings: UserSettings? = null

    fun get(): UserSettings =
        cachedSettings ?: getUserSettingsUseCase.execute().also { cachedSettings = it }

    private fun invalidateCache() {
        cachedSettings = null
    }

    fun getShowTips(): Boolean {
        val execute = getUserSettingsShowTipsUseCase.execute()
        return execute
    }

    fun save(userSettings: UserSettings) {
        saveUserSettingsUseCase.execute(userSettings = userSettings)
        invalidateCache()
    }

    fun saveFirst(first: Boolean) {
        saveFirstUseCase.execute(value = first)
    }

    fun saveAppTheme(applicationTheme: Boolean) {
        saveUserSettingsAppThemeUseCase.execute(appTheme = applicationTheme)
        invalidateCache()
    }

    fun saveShowTips(showTips: Boolean) {
        saveUserSettingsShowTipsUseCase.execute(showTips = showTips)
        invalidateCache()
    }

    fun saveWeatherUnits(weatherUnits: WeatherUnits) {
        saveUserSettingsWeatherUnitsUseCase.execute(weatherUnits = weatherUnits)
        invalidateCache()
    }

    fun saveWindUnits(windSpeed: WindSpeed) {
        saveUserSettingsWindUnitsUseCase.execute(value = windSpeed)
        invalidateCache()
    }

    fun saveLocation(location: Location) {
        saveUserSettingsLocationUseCase.execute(value = location)
        invalidateCache()
    }

    fun saveWidget(widgetConfig: WidgetConfig) {
        saveWidgetConfigUseCase.execute(widgetConfig = widgetConfig)
    }

    fun getWidget(): WidgetConfig {
        val widgetConfig = getWidgetConfigUseCase.execute()
        return widgetConfig
    }

    fun getFirst(): Boolean {
        val first = getFirstUseCase.execute()
        return first
    }

    fun getNotificationItem(): NotificationItem {
        val notificationItem = getNotificationItemUseCase.execute()
        return notificationItem
    }

    fun saveNotificationItem(notificationItem: NotificationItem) {
        saveNotificationItemUseCase.execute(notificationItem = notificationItem)
    }
}
