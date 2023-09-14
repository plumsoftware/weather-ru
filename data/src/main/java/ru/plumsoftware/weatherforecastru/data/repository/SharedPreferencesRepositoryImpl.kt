package ru.plumsoftware.weatherforecastru.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.core.content.edit
import ru.plumsoftware.weatherforecastru.domain.constants.Constants
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

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

            val windSpeed: WindSpeed = WindSpeed(
                windPresentation = getString(
                    Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_PRESENTATION,
                    Constants.Settings.M_S.first
                )!!,
                windValue = getFloat(
                    Constants.SharedPreferences.SHARED_PREF_WIND_SPEED_VALUE,
                    Constants.Settings.M_S.second
                )
            )

            logd("application theme: " + if (theme) "DARK" else "LIGHT")
            logd("base city: $city")
            logd("base country: $country")
            logd("show tips: $showTips")

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
            )
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
                .apply()
        }
    }

    override fun saveFist(first: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_FIRST, first)
            .apply()
    }
}