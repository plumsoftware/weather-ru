package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class GetUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): Boolean {
        val showTipsUseCase: Boolean = sharedPreferencesRepository.getShowTips()
        return showTipsUseCase
    }
}