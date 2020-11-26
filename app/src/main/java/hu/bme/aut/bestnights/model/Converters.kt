package hu.bme.aut.bestnights.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
        @TypeConverter
        fun fromTimestamp(date: Long?): Date? {
            return date?.let {
                Date(it)
            }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

        @TypeConverter
        fun restoreStringList(stringList: String?): List<String?>? {
            return Gson().fromJson(stringList, object : TypeToken<List<String?>?>() {}.getType())
        }

        @TypeConverter
        fun saveStringList(stringList: List<String?>?): String? {
            return Gson().toJson(stringList)
        }

}