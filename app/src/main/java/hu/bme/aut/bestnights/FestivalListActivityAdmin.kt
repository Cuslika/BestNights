package hu.bme.aut.bestnights

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.bestnights.adapter.AdminFestivalAdapter
import hu.bme.aut.bestnights.data.FestivalDatabase
import hu.bme.aut.bestnights.fragments.EditFestivalDialogFragment
import hu.bme.aut.bestnights.fragments.NewFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_festival_admin.*
import kotlinx.android.synthetic.main.content_festival_list_admin.*
import kotlin.concurrent.thread

class FestivalListActivityAdmin : AppCompatActivity(), AdminFestivalAdapter.AdminFestivalClickListener, NewFestivalDialogFragment.NewFestivalDialogListener, EditFestivalDialogFragment.EditFestivalDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterAdmin: AdminFestivalAdapter
    private lateinit var database: FestivalDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festival_admin)

        setTitle("Upcoming festivals")

        user = intent.getSerializableExtra("User") as User

        faddm.setOnClickListener {
            NewFestivalDialogFragment().show(
                supportFragmentManager,
                NewFestivalDialogFragment.TAG
            )
        }

        var nv: NavigationView = findViewById(R.id.navView)
        var nh: View = nv.getHeaderView(0)
        var ntv: TextView = nh.findViewById(R.id.yourName)
        ntv.setText(user.name)

        drawer = findViewById(R.id.fadrawerLayout)

        toggle = ActionBarDrawerToggle(this, fadrawerLayout, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        nv.setNavigationItemSelectedListener(this)
        nv.bringToFront()

        database =
            Room.databaseBuilder(applicationContext, FestivalDatabase::class.java, "festival-list")
                .build()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = FAdminRecyclerView
        adapterAdmin = AdminFestivalAdapter(this, supportFragmentManager)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterAdmin
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.festivalDao().getAll()
            runOnUiThread {
                adapterAdmin.update(items)
            }
        }
    }

    override fun onItemDeleted(item: Festival) {
        thread {
            database.festivalDao().deleteFestival(item)
            runOnUiThread {
                adapterAdmin.removeItem(item)
            }
            Log.d("Debug", "Festival delete was successful")
        }
    }

    override fun onFestivalCreated(newFestival: Festival) {
        thread {
            val newId = database.festivalDao().insert(newFestival)
            val newF = newFestival.copy(
                id = newId
            )
            runOnUiThread {
                adapterAdmin.addFestival(newF)
            }
        }
    }

    override fun onFestivalEdited(festival: Festival) {
        thread {
            database.festivalDao().update(festival)
            runOnUiThread {
                adapterAdmin.update(festival)
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
