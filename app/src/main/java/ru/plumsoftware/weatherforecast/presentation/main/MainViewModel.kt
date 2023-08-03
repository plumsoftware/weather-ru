package ru.plumsoftware.weatherforecast.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import ru.plumsoftware.weatherforecast.data.constants.Constants
import ru.plumsoftware.weatherforecast.data.utilities.logd

class MainViewModel(@SuppressLint("StaticFieldLeak") private val context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            Constants.SharedPreferences.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveThemeInSharedPreferences(theme: Boolean) {
        logd("application theme: " + if (theme) "DARK" else "LIGHT")
        sharedPreferences.edit().putBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, theme)
            .apply()
    }

    fun getThemeFromSharedPreferences(): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            Constants.SharedPreferences.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        val theme =
            sharedPreferences.getBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, false)
        logd("application theme: " + if (theme) "DARK" else "LIGHT")
        return theme
    }
}