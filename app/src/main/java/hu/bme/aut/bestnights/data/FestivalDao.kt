package hu.bme.aut.bestnights.data

import androidx.room.*
import hu.bme.aut.bestnights.model.Festival

@Dao
interface FestivalDao {
    @Query("SELECT * FROM festival")
    fun getAll(): List<Festival>

    @Insert
    fun insert(festival: Festival): Long

    @Update
    fun update(festival: Festival)

    @Delete
    fun deleteFestival(festival: Festival)

}