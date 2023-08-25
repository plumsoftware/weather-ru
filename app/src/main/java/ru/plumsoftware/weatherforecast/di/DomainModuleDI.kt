package ru.plumsoftware.weatherforecast.di

import org.koin.dsl.module
import ru.plumsoftware.weatherforecast.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecast.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.domain.usecase.location.GetLastKnownLocationUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsWindUnitsUseCase

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
            )
        )
    }
}