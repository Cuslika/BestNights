package hu.bme.aut.bestnights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.bestnights.adapter.AdminPartyAdapter
import hu.bme.aut.bestnights.data.PartyDatabase
import hu.bme.aut.bestnights.fragments.NewPartyDialogFragment
import hu.bme.aut.bestnights.model.Party
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_party_list.*
import kotlinx.android.synthetic.main.content_party_list_admin.*
import kotlin.concurrent.thread

class PartyListActivity : AppCompatActivity(), AdminPartyAdapter.PartyClickListener, NewPartyDialogFragment.NewPartyDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterAdmin: AdminPartyAdapter
    private lateinit var database: PartyDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_list)

        setTitle("Upcoming parties")

        val user = intent.getSerializableExtra("User") as User


        var nv: NavigationView = findViewById(R.id.navView)
        var nh: View = nv.getHeaderView(0)
        var ntv: TextView = nh.findViewById(R.id.yourName)
        ntv.setText(user.name)

        drawer = findViewById(R.id.ppdrawerLayout)

        toggle = ActionBarDrawerToggle(this, ppdrawerLayout, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        nv.setNavigationItemSelectedListener(this)
        nv.bringToFront()

        database =
            Room.databaseBuilder(applicationContext, PartyDatabase::class.java, "party-list")
                .build()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = PAdminRecyclerView
        adapterAdmin = AdminPartyAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterAdmin
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.partyDao().getAll()
            runOnUiThread {
                adapterAdmin.update(items)
            }
        }
    }

    override fun onItemChanged(item: Party) {
        thread {
            database.partyDao().update(item)
            Log.d("Debug", "Party update was successful")
        }
    }

    override fun onItemDeleted(item: Party) {
        thread {
            database.partyDao().deleteParty(item)
            runOnUiThread {
                adapterAdmin.removeItem(item)
            }
            Log.d("Debug", "Party delete was successful")
        }
    }

    override fun onPartyCreated(newParty: Party) {
        thread {
            val newId = database.partyDao().insert(newParty)
            val newP = newParty.copy(
                id = newId
            )
            runOnUiThread {
                adapterAdmin.addParty(newP)
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
            R.id.profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            R.id.tickets -> Toast.makeText(this, "Tickets", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}