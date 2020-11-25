package hu.bme.aut.bestnights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.bestnights.data.FestivalDatabase
import hu.bme.aut.bestnights.data.PartyDatabase

class ListActivity : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var database: RoomDatabase
    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    companion object {
        const val EVENT_TYPE = "EVENT_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_festival_list)

        val eventType = this.intent.getIntExtra(EVENT_TYPE, -1)

        setTitle(getTypeString(eventType))

        database = getDatabase(eventType)

    }

    private fun getTypeString(eventType: Int): String? {
        return when (eventType) {
            DecisionActivity.FESTIVAL -> "Festivals coming up:"
            DecisionActivity.PARTY -> "Parties coming up:"
            else -> "Unknown"
        }
    }

    private fun getDatabase(eventType: Int): RoomDatabase {
        return when(eventType) {
            DecisionActivity.FESTIVAL -> Room.databaseBuilder(applicationContext, FestivalDatabase::class.java, "festival-list").build()
            DecisionActivity.PARTY -> Room.databaseBuilder(applicationContext, PartyDatabase::class.java, "party-list").build()
            else -> Room.databaseBuilder(applicationContext, FestivalDatabase::class.java, "").build()
        }
    }

}