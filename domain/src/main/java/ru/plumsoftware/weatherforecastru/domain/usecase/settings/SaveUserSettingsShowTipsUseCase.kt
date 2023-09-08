package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(showTips: Boolean) {
        sharedPreferencesRepository.saveUserSettingsShowTips(showTips = showTips)
    }
}