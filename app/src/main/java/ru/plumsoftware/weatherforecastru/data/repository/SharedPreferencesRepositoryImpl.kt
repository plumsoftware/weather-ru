package ru.plumsoftware.weatherforecastru.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig

class SharedPreferencesRepositoryImpl(private val context: Context) : SharedPreferencesRepository {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            Constants.SharedPreferences.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun saveUserSettings(userSettings: UserSettings) {
        sharedPreferences.edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, userSettings.isDarkTheme)
            .putString(Constants.SharedPreferences.SHARED_PREF_CITY, userSettings.city)
            .putString(Constants.SharedPreferences.SHARED_PREF_COUNTRY, userSettings.country)
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_SHOW_TIPS, userSettings.showTips)
//            region::Weather
            .putString(
                Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_PRESENTATION,
                userSettings.weatherUnits.unitsPresentation
            )
            .putString(
                Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_VALUE,
                userSettings.weatherUnits.unitsValue
            )
            .putString(
                Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_PRESENTATION,
                userSettings.windSpeed.windPresentation
            )
            .putFloat(
                Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_VALUE,
                userSettings.windSpeed.windValue
            )
//            endregion
            .apply()
    }

    override fun getUserSettings(): UserSettings {
        with(sharedPreferences) {
            val theme =
                getBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, false)
            val city = getString(Constants.SharedPreferences.SHARED_PREF_CITY, "")
            val country =
                getString(Constants.SharedPreferences.SHARED_PREF_COUNTRY, "")
            val showTips =
                getBoolean(
                    Constants.SharedPreferences.SHARED_PREF_SHOW_TIPS,
                    false
                )

            val weatherUnits: WeatherUnits = WeatherUnits(
                unitsPresentation = getString(
                    Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_PRESENTATION,
                    Constants.Settings.METRIC.second
                )!!,
                unitsValue = getString(
                    Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_VALUE,
                    Constants.Settings.METRIC.first
                )!!
            )

            val rawWindPresentation = getString(
                Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_PRESENTATION,
                Constants.Settings.M_S.first
            )!!
            val normalizedPresentation = normalizeWindPresentation(rawWindPresentation)
            val windSpeed: WindSpeed = WindSpeed(
                windPresentation = normalizedPresentation,
                windValue = if (normalizedPresentation == Constants.Settings.KM_H.first) {
                    Constants.Settings.KM_H.second
                } else {
                    Constants.Settings.M_S.second
                },
            )

            return UserSettings(
                isDarkTheme = theme,
                city = city,
                country = country,
                showTips = showTips,
                weatherUnits = weatherUnits,
                windSpeed = windSpeed
            )
        }
    }

    override fun getShowTips(): Boolean {
        val showTips =
            sharedPreferences.getBoolean(
                Constants.SharedPreferences.SHARED_PREF_SHOW_TIPS,
                true
            )
        return showTips
    }

    override fun getWidgetConfig(): WidgetConfig {
        val widgetConfig = WidgetConfig(
            radius = sharedPreferences.getInt(
                Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_RADIUS,
                20
            ),
            red = sharedPreferences.getInt(
                Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_RED,
                255
            ),
            green = sharedPreferences.getInt(
                Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_GREEN,
                255
            ),
            blue = sharedPreferences.getInt(
                Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_BLUE,
                255
            ),
            opacity = sharedPreferences.getFloat(
                Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_OPACITY,
                1f
            ),
        )
        logd("Widget color: ${widgetConfig.red}, ${widgetConfig.green}, ${widgetConfig.blue}")
        logd("Widget radius: ${widgetConfig.radius}")
        return widgetConfig
    }

    override fun getFirst(): Boolean {
        val first: Boolean =
            sharedPreferences.getBoolean(Constants.SharedPreferences.SHARED_PREF_FIRST, true)
        return first
    }

    override fun getNotificationPeriod(): NotificationItem {
        val period = sharedPreferences.getLong(
            Constants.SharedPreferences.SHARED_PREF_NOTIFICATION_PERIOD,
            21600000
        )
        val notificationNaming = sharedPreferences.getInt(
            Constants.SharedPreferences.SHARED_PREF_NOTIFICATION_NAMING,
            0
        )
        return NotificationItem(
            namingResId = notificationNaming,
            period = period
        )
    }

    override fun saveUserSettingsAppTheme(appTheme: Boolean) {
        sharedPreferences.edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, appTheme)
            .apply()
    }

    override fun saveUserSettingsShowTips(showTips: Boolean) {
        sharedPreferences.edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_SHOW_TIPS, showTips)
            .apply()
    }

    override fun saveUserSettingsWeatherUnits(weatherUnits: WeatherUnits) {
        with(weatherUnits) {
            sharedPreferences.edit()
                .putString(
                    Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_PRESENTATION,
                    unitsPresentation
                )
                .putString(
                    Constants.SharedPreferences.SHARED_PREF_WEATHER_UNITS_VALUE,
                    unitsValue
                )
                .apply()
        }
    }

    override fun saveUserSettingsWindSpeed(windSpeed: WindSpeed) {
        with(windSpeed) {
            sharedPreferences.edit()
                .putString(
                    Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_PRESENTATION,
                    windPresentation
                )
                .putFloat(
                    Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_VALUE,
                    windValue
                )
                .apply()
        }
    }

    override fun saveLocation(location: Location) {
        with(location) {
            sharedPreferences
                .edit()
                .putString(Constants.SharedPreferences.SHARED_PREF_CITY, city)
                .putString(Constants.SharedPreferences.SHARED_PREF_COUNTRY, country)
                .apply()
        }
    }

    override fun saveWidgetConfig(widgetConfig: WidgetConfig) {
        with(widgetConfig) {
            sharedPreferences
                .edit()
                .putInt(Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_RADIUS, radius)
                .putInt(Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_RED, red)
                .putInt(Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_GREEN, green)
                .putInt(Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_COLOR_BLUE, blue)
                .putFloat(Constants.SharedPreferences.SHARED_PREF_APP_WIDGET_OPACITY, opacity)
                .apply()
        }
    }

    override fun saveFist(first: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_FIRST, first)
            .apply()
    }

    override fun getLaunchCount(): Int =
        sharedPreferences.getInt(Constants.SharedPreferences.SHARED_PREF_LAUNCH_COUNT, 0)

    override fun incrementLaunchCount(): Int {
        val count = getLaunchCount() + 1
        sharedPreferences.edit()
            .putInt(Constants.SharedPreferences.SHARED_PREF_LAUNCH_COUNT, count)
            .apply()
        return count
    }

    override fun saveNotificationPeriod(notificationItem: NotificationItem) {
        sharedPreferences
            .edit()
            .putLong(
                Constants.SharedPreferences.SHARED_PREF_NOTIFICATION_PERIOD,
                notificationItem.period
            )
            .putInt(
                Constants.SharedPreferences.SHARED_PREF_NOTIFICATION_NAMING,
                notificationItem.namingResId
            )
            .apply()
    }
}

private fun normalizeWindPresentation(raw: String): String = when (raw) {
    "м/c", Constants.Settings.M_S.first -> Constants.Settings.M_S.first
    "миль/час", Constants.Settings.KM_H.first -> Constants.Settings.KM_H.first
    else -> raw
}