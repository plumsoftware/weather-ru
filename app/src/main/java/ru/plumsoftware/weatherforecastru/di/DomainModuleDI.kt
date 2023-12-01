package ru.plumsoftware.weatherforecastru.di

import org.koin.dsl.module
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecastru.domain.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.domain.usecase.location.GetLastKnownLocationUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.domain.usecase.widget.SaveWidgetConfigUseCase

internal val domainModuleDI = module {
    single<LocationRepository> { LocationRepositoryImpl(context = get()) }
    single<LocationStorage> {
        LocationStorage(
            getLastKnownLocationUseCase = GetLastKnownLocationUseCase(
                locationRepository = get()
            )
        )
    }

    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(context = get()) }
    single<SharedPreferencesStorage> {
        SharedPreferencesStorage(
            getUserSettingsUseCase = GetUserSettingsUseCase(sharedPreferencesRepository = get()),
            getUserSettingsShowTipsUseCase = GetUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = get()
            ),
            getFirstUseCase = GetFirstUseCase(sharedPreferencesRepository = get()),
            saveUserSettingsUseCase = SaveUserSettingsUseCase(sharedPreferencesRepository = get()),
            saveUserSettingsAppThemeUseCase = SaveUserSettingsAppThemeUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsShowTipsUseCase = SaveUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsWeatherUnitsUseCase = SaveUserSettingsWeatherUnitsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsWindUnitsUseCase = SaveUserSettingsWindUnitsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsLocationUseCase = SaveUserSettingsLocationUseCase(
                sharedPreferencesRepository = get()
            ),
            saveFirstUseCase = SaveFirstUseCase(sharedPreferencesRepository = get()),
            saveWidgetConfigUseCase = SaveWidgetConfigUseCase(sharedPreferencesRepository = get()),
            getWidgetConfigUseCase = GetWidgetConfigUseCase(sharedPreferencesRepository = get()),
            getNotificationItemUseCase = GetNotificationItemUseCase(sharedPreferencesRepository = get()),
            saveNotificationItemUseCase = SaveNotificationItemUseCase(
                sharedPreferencesRepository = get()
            )
        )
    }
}