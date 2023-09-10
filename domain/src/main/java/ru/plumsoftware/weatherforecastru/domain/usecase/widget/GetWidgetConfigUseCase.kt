package ru.plumsoftware.weatherforecastru.domain.usecase.widget

import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class GetWidgetConfigUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute () : WidgetConfig {
        val widgetConfig : WidgetConfig = sharedPreferencesRepository.getWidgetConfig()
        return widgetConfig
    }
}