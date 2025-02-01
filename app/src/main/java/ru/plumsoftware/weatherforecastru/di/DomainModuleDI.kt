package ru.plumsoftware.weatherforecastru.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.storage.LocationStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.data.usecase.location.GetLastKnownLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase

val domainModuleDI = module {

    singleOf(::LocationRepositoryImpl).bind<LocationRepository>()

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
            getUserSettingsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase(
                sharedPreferencesRepository = get()
            ),
            getUserSettingsShowTipsUseCase = GetUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = get()
            ),
            getFirstUseCase = GetFirstUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsUseCase = SaveUserSettingsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsAppThemeUseCase = SaveUserSettingsAppThemeUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsShowTipsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsWeatherUnitsUseCase = SaveUserSettingsWeatherUnitsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsWindUnitsUseCase = SaveUserSettingsWindUnitsUseCase(
                sharedPreferencesRepository = get()
            ),
            saveUserSettingsLocationUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase(
                sharedPreferencesRepository = get()
            ),
            saveFirstUseCase = SaveFirstUseCase(
                sharedPreferencesRepository = get()
            ),
            saveWidgetConfigUseCase = SaveWidgetConfigUseCase(
                sharedPreferencesRepository = get()
            ),
            getWidgetConfigUseCase = GetWidgetConfigUseCase(
                sharedPreferencesRepository = get()
            ),
            getNotificationItemUseCase = GetNotificationItemUseCase(
                sharedPreferencesRepository = get()
            ),
            saveNotificationItemUseCase = SaveNotificationItemUseCase(
                sharedPreferencesRepository = get()
            )
        )
    }
}