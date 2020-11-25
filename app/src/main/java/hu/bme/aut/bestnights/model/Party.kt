package hu.bme.aut.bestnights.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName ="party")
data class Party (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "amount") val amount: Int
   //@ColumnInfo(name = "capacity") val capacity: Int,
    //@ColumnInfo(name = "location") val location: String,
    //@ColumnInfo(name = "date") val date: Date
) : Serializable