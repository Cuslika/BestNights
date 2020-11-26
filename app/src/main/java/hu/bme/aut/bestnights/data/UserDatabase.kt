package hu.bme.aut.bestnights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.bestnights.data.UserDao
import hu.bme.aut.bestnights.model.Converters
import hu.bme.aut.bestnights.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}