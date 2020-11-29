package hu.bme.aut.bestnights

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.bestnights.adapter.festival.DetailsFestivalAdapter
import hu.bme.aut.bestnights.adapter.festival.FestivalAdapter
import hu.bme.aut.bestnights.adapter.party.DetailsPartyAdapter
import hu.bme.aut.bestnights.adapter.party.PartyAdapter
import hu.bme.aut.bestnights.data.FestivalDatabase
import hu.bme.aut.bestnights.data.PartyDatabase
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_tickets.*
import kotlin.concurrent.thread

class TicketsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var frecyclerView: RecyclerView
    private lateinit var precyclerView: RecyclerView
    private lateinit var festivalAdapter: DetailsFestivalAdapter
    private lateinit var partyAdapter: DetailsPartyAdapter
    private lateinit var fdatabase: FestivalDatabase
    private lateinit var pdatabase: PartyDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)

        setTitle("Purchased tickets")

        user = intent.getSerializableExtra("User") as User

        Log.d("Debug", user.festivals.toString())

        var nv: NavigationView = findViewById(R.id.navView)
        var nh: View = nv.getHeaderView(0)
        var ntv: TextView = nh.findViewById(R.id.yourName)
        ntv.setText(user.name)

        drawer = findViewById(R.id.fdrawerLayout)

        toggle = ActionBarDrawerToggle(this, fdrawerLayout, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        nv.setNavigationItemSelectedListener(this)
        nv.bringToFront()

        fdatabase =
            Room.databaseBuilder(applicationContext, FestivalDatabase::class.java, "festival-list")
                .build()

        pdatabase =
            Room.databaseBuilder(applicationContext, PartyDatabase::class.java, "party-list")
                .build()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        frecyclerView = festivalRecycler
        precyclerView = partyRecycler
        festivalAdapter = DetailsFestivalAdapter(supportFragmentManager)
        partyAdapter = DetailsPartyAdapter(supportFragmentManager)
        loadItemsInBackground()
        frecyclerView.layoutManager = LinearLayoutManager(this)
        frecyclerView.adapter = festivalAdapter
        precyclerView.layoutManager = LinearLayoutManager(this)
        precyclerView.adapter = partyAdapter
    }

    private fun loadItemsInBackground() {
        Log.d("Debug", user.parties.toString())
        var fs = user.festivals
        var ps = user.parties
        thread {
            val fitems = fdatabase.festivalDao().getAll()
            val pitems = pdatabase.partyDao().getAll()
            runOnUiThread {
                festivalAdapter.updateAll(fitems, fs)
                partyAdapter.updateAll(pitems, ps)
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.tickets -> {
                val intent = Intent(this, TicketsActivity::class.java)
                intent.putExtra("User", user)
                finish()
                startActivity(intent)
            }
            R.id.logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}