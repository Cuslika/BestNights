package hu.bme.aut.bestnights.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.bestnights.model.Converters
import hu.bme.aut.bestnights.model.Festival

@Database(entities = [Festival::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FestivalDatabase : RoomDatabase() {
    abstract fun festivalDao(): FestivalDao
    }