package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository

class GetFirstUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): Boolean {
        val first: Boolean = sharedPreferencesRepository.getFirst()
        return first
    }
}