package com.evanisnor.flickwatcher.cache.database

import androidx.room.TypeConverter
import java.time.LocalDate

class CustomTypeConverters {

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?) = localDate?.toEpochDay()

    @TypeConverter
    fun toLocalDate(timestamp: Long?) = if (timestamp == null) {
        null
    } else {
        LocalDate.ofEpochDay(timestamp)
    }

}