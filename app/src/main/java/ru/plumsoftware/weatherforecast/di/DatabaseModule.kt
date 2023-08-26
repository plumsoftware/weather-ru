package ru.plumsoftware.weatherforecast.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.plumsoftware.weatherforecast.data.database.LocationItemDatabase
import ru.plumsoftware.weatherforecast.domain.constants.Constants

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocationItemDatabase::class.java,
            Constants.Database.DATABASE_NAME
        ).build()
    }

    single { get<LocationItemDatabase>().dao }
}