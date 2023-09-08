package ru.plumsoftware.weatherforecastru.data.models.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
)