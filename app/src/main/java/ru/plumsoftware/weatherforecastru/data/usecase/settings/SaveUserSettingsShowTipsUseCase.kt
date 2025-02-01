package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository

class SaveUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(showTips: Boolean) {
        sharedPreferencesRepository.saveUserSettingsShowTips(showTips = showTips)
    }
}