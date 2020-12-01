package hu.bme.aut.bestnights.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonArray
import java.io.Serializable

@Entity(tableName = "user")
data class User (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "festivals") var festivals: ArrayList<String?>?,
    @ColumnInfo(name = "parties") var parties: ArrayList<String?>?
): Serializable