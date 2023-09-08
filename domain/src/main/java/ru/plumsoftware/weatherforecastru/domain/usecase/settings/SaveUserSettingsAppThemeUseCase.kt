package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsAppThemeUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(appTheme: Boolean) {
        sharedPreferencesRepository.saveUserSettingsAppTheme(appTheme = appTheme)
    }
}