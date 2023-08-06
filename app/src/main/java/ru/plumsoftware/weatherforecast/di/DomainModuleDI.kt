package ru.plumsoftware.weatherforecast.di

import org.koin.dsl.module
import ru.plumsoftware.weatherforecast.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecast.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.domain.usecase.location.GetLastKnownLocationUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsUseCase

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
            saveUserSettingsUseCase = SaveUserSettingsUseCase(sharedPreferencesRepository = get())
        )
    }
}