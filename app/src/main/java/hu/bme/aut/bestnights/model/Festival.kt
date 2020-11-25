package hu.bme.aut.bestnights.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName ="festival")
data class Festival (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "earlybirdPrice") var earlybirdPrice: Int
    //@ColumnInfo(name = "normalPrice") val normalPrice: Int,
    //@ColumnInfo(name = "earlybirdAmount") val earlybirdAmount: Int,
    //@ColumnInfo(name = "normalAmount") val normalAmount: Int,
    //@ColumnInfo(name = "location") val location: String,
    //@ColumnInfo(name = "startDate") val startDate: Date,
    //@ColumnInfo(name = "endDate") val endDate: Date
) : Serializable
