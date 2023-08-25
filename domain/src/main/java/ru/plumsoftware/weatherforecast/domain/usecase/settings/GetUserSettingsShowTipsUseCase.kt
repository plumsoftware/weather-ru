package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class GetUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): Boolean {
        val showTipsUseCase: Boolean = sharedPreferencesRepository.getShowTips()
        return showTipsUseCase
    }
}