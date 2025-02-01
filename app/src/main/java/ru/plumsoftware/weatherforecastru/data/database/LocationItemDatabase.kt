package ru.plumsoftware.weatherforecastru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItem
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItemDao

@Database(
    entities = [LocationItem::class],
    version = 1,
    exportSchema = true
)
abstract class LocationItemDatabase : RoomDatabase(){
    abstract val dao : LocationItemDao
}