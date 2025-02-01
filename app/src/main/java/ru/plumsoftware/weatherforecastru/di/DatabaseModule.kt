package ru.plumsoftware.weatherforecastru.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherforecastru.data.database.LocationItemDatabase

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