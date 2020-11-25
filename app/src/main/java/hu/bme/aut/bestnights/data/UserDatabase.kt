package hu.bme.aut.bestnights.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.bestnights.data.UserDao
import hu.bme.aut.bestnights.model.User

@Database(entities = [User::class], version = 1)
public abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}