package hu.bme.aut.bestnights.data

import androidx.room.*
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.Party
import hu.bme.aut.bestnights.model.User
import java.util.ArrayList

@Dao
interface UserDao {

    @Query("Select * From User WHERE name LIKE :name and password LIKE :password")
    fun getUser(name: String, password: String): User

    @Query("Select * From User")
    fun getUsers(): List<User>

    @Insert
    fun insert(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: User)

    @Delete
    fun deleteUser(user: User)

}