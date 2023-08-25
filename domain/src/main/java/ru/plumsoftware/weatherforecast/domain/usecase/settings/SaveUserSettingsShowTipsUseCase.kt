package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(showTips: Boolean) {
        sharedPreferencesRepository.saveUserSettingsShowTips(showTips = showTips)
    }
}