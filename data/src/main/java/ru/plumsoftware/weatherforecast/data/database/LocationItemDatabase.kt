package ru.plumsoftware.weatherforecast.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.plumsoftware.weatherforecast.data.models.location.LocationItem
import ru.plumsoftware.weatherforecast.data.models.location.LocationItemDao

@Database(
    entities = [LocationItem::class],
    version = 1
)
abstract class LocationItemDatabase : RoomDatabase(){
    abstract val dao : LocationItemDao
}