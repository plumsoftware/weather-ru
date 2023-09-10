package ru.plumsoftware.weatherforecastru.domain.usecase.widget

import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveWidgetConfigUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(widgetConfig: WidgetConfig) {
        sharedPreferencesRepository.saveWidgetConfig(widgetConfig = widgetConfig)
    }
}