package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class GetFirstUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): Boolean {
        val first: Boolean = sharedPreferencesRepository.getFirst()
        return first
    }
}