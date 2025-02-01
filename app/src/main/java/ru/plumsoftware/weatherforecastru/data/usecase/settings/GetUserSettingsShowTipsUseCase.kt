package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository

class GetUserSettingsShowTipsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): Boolean {
        val showTipsUseCase: Boolean = sharedPreferencesRepository.getShowTips()
        return showTipsUseCase
    }
}