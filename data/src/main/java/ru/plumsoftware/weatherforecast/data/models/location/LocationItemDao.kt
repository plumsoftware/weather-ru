package ru.plumsoftware.weatherforecast.data.models.location

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocationItemDao {

    @Upsert
    suspend fun upsert(locationItem: LocationItem)

    @Delete
    suspend fun delete(locationItem: LocationItem)

    @Query("SELECT * FROM LocationItem ORDER BY id ASC")
    suspend fun getAll(): MutableLiveData<List<LocationItem>>
}
