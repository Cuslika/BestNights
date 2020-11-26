package hu.bme.aut.bestnights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import hu.bme.aut.bestnights.adapter.FestivalAdapter
import hu.bme.aut.bestnights.data.FestivalDatabase
import hu.bme.aut.bestnights.fragments.festival.NewFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_festival_list.*
import kotlinx.android.synthetic.main.content_festival_list_admin.*

class FestivalListActivity : AppCompatActivity(), FestivalAdapter.FestivalClickListener, NewFestivalDialogFragment.NewFestivalDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FestivalAdapter
    private lateinit var database: FestivalDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festival_list)

        setTitle("Upcoming festivals")

        val user = intent.getSerializableExtra("User") as User


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

        database =
            Room.databaseBuilder(applicationContext, FestivalDatabase::class.java, "festival-list")
                .build()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = FAdminRecyclerView
        adapter = FestivalAdapter(this, supportFragmentManager)
        //loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /*private fun loadItemsInBackground() {
        thread {
            val items = database.festivalDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemDeleted(item: Festival) {
        thread {
            database.festivalDao().deleteFestival(item)
            runOnUiThread {
                adapter.removeItem(item)
            }
            Log.d("Debug", "Festival delete was successful")
        }
    }*/

    override fun onFestivalCreated(newFestival: Festival) {
        /*thread {
            val newId = database.festivalDao().insert(newFestival)
            val newF = newFestival.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addFestival(newF)
            }
        }*/
    }
/*
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            R.id.tickets -> Toast.makeText(this, "Tickets", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}