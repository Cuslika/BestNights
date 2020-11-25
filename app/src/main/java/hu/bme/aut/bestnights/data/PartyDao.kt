package hu.bme.aut.bestnights.data

import androidx.room.*
import hu.bme.aut.bestnights.model.Party

@Dao
interface PartyDao {
    @Query("SELECT * FROM party")
    fun getAll(): List<Party>

    @Insert
    fun insert(party: Party): Long

    @Update
    fun update(party: Party)

    @Delete
    fun deleteParty(party: Party)
}