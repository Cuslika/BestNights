package hu.bme.aut.bestnights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.bestnights.model.Festival

@Database(entities = [Festival::class], version = 1)
abstract class FestivalDatabase : RoomDatabase() {
    abstract fun festivalDao(): FestivalDao
}