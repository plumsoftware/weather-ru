package ru.plumsoftware.weatherforecast.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.plumsoftware.weatherforecast.domain.constants.Constants
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

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
                    true
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
}