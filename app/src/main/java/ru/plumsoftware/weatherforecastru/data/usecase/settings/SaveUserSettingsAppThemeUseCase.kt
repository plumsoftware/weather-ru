package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository

class SaveUserSettingsAppThemeUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(appTheme: Boolean) {
        sharedPreferencesRepository.saveUserSettingsAppTheme(appTheme = appTheme)
    }
}