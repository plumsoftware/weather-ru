package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository

class SaveFirstUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: Boolean) {
        sharedPreferencesRepository.saveFist(first = value)
    }
}