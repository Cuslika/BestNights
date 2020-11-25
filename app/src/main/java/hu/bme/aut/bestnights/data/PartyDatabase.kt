package hu.bme.aut.bestnights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.Party

@Database(entities = [Party::class], version = 1)
abstract class PartyDatabase : RoomDatabase() {
    abstract fun partyDao(): PartyDao
}