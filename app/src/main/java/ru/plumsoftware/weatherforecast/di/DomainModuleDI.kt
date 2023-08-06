package ru.plumsoftware.weatherforecast.di

import org.koin.dsl.module
import ru.plumsoftware.weatherforecast.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.usecase.GetLastKnownLocationUseCase

internal val domainModuleDI = module {
    single<LocationRepository> { LocationRepositoryImpl(context = get()) }
    single<LocationStorage> {
        LocationStorage(
            getLastKnownLocationUseCase = GetLastKnownLocationUseCase(
                locationRepository = get()
            )
        )
    }
}