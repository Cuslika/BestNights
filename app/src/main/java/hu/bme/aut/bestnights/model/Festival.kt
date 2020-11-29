package hu.bme.aut.bestnights.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName ="festival")
data class Festival (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "normalPrice") var normalPrice: Int,
    @ColumnInfo(name = "normalAmount") var normalAmount: Int
    //@ColumnInfo(name = "location") var location: String,
    //@ColumnInfo(name = "startDate") var startDate: Date,
    //@ColumnInfo(name = "endDate") var endDate: Date
) : Serializable
