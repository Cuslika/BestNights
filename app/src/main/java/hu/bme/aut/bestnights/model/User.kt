package hu.bme.aut.bestnights.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    //@ColumnInfo(name = "festivals") val festivals: List<Festival>,
    //@ColumnInfo(name = "parties") val parties: List<Party>
): Serializable