package ru.plumsoftware.weatherforecastru.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.plumsoftware.weatherforecastru.data.database.LocationItemDatabase
import ru.plumsoftware.weatherforecastru.domain.constants.Constants

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