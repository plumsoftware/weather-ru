package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveFirstUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: Boolean) {
        sharedPreferencesRepository.saveFist(first = value)
    }
}