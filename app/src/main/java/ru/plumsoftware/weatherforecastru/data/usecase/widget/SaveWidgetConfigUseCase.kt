package ru.plumsoftware.weatherforecastru.data.usecase.widget

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig

class SaveWidgetConfigUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(widgetConfig: WidgetConfig) {
        sharedPreferencesRepository.saveWidgetConfig(widgetConfig = widgetConfig)
    }
}