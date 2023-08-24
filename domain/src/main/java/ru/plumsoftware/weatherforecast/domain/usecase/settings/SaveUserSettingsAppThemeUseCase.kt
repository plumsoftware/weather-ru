package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsAppThemeUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(appTheme: Boolean) {
        sharedPreferencesRepository.saveUserSettingsAppTheme(appTheme = appTheme)
    }
}