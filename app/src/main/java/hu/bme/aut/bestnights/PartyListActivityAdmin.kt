package hu.bme.aut.bestnights

import android.content.Intent
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
import hu.bme.aut.bestnights.fragments.festival.EditFestivalDialogFragment
import hu.bme.aut.bestnights.fragments.party.EditPartyDialogFragment
import hu.bme.aut.bestnights.fragments.party.NewPartyDialogFragment
import hu.bme.aut.bestnights.model.Party
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_party_admin.*
import kotlinx.android.synthetic.main.content_party_list_admin.*
import kotlin.concurrent.thread

class PartyListActivityAdmin : AppCompatActivity(), AdminPartyAdapter.PartyClickListener, NewPartyDialogFragment.NewPartyDialogListener, EditPartyDialogFragment.EditPartyDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterAdmin: AdminPartyAdapter
    private lateinit var database: PartyDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_admin)

        setTitle("Upcoming parties")

        user = intent.getSerializableExtra("User") as User

        paddm.setOnClickListener {
            NewPartyDialogFragment().show(
                supportFragmentManager,
                NewPartyDialogFragment.TAG
            )
        }

        var nv: NavigationView = findViewById(R.id.navView)
        var nh: View = nv.getHeaderView(0)
        var ntv: TextView = nh.findViewById(R.id.yourName)
        ntv.setText(user.name)

        drawer = findViewById(R.id.pdrawerLayout)

        toggle = ActionBarDrawerToggle(this, pdrawerLayout, R.string.open, R.string.close)
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
        adapterAdmin = AdminPartyAdapter(this, supportFragmentManager)
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

    override fun onPartyEdited(party: Party) {
        thread {
            database.partyDao().update(party)
            runOnUiThread {
                adapterAdmin.update(party)
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
            R.id.profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("User", user)
                startActivity(intent)
            }
            R.id.tickets -> {
                val intent = Intent(this, TicketsActivity::class.java)
                intent.putExtra("User", user)
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