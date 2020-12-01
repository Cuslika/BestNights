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
import hu.bme.aut.bestnights.adapter.festival.FestivalAdapter
import hu.bme.aut.bestnights.data.FestivalDatabase
import hu.bme.aut.bestnights.data.UserDatabase
import hu.bme.aut.bestnights.fragments.festival.ShowFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_festival_list.*
import kotlinx.android.synthetic.main.content_festival_list.*
import kotlin.concurrent.thread

class FestivalListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ShowFestivalDialogFragment.PurchaseFestivalDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FestivalAdapter
    private lateinit var database: FestivalDatabase
    private lateinit var userDatabase: UserDatabase
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festival_list)

        setTitle("Upcoming festivals")

        user = intent.getSerializableExtra("User") as User


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
        userDatabase = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user-list").build()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = FRecyclerView
        adapter = FestivalAdapter(supportFragmentManager)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread{
            val items = database.festivalDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onFestivalPurchased(festival: Festival) {
        thread {
            runOnUiThread {
                adapter.purchaseFestival(festival)
                if(user.festivals.isNullOrEmpty()){
                    val stringList = ArrayList<String?>()
                    stringList.add(festival.name)
                    user.festivals = stringList
                } else {
                    user.festivals?.add(festival.name)
                }
            }
            userDatabase.userDao().update(user)
            database.festivalDao().update(festival)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DecisionActivity::class.java)
        intent.putExtra("User", user)
        finish()
        startActivity(intent)
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
            R.id.home -> {
                val intent = Intent(this, DecisionActivity::class.java)
                intent.putExtra("User", user)
                finish()
                startActivity(intent)
            }
            R.id.tickets -> {
                val intent = Intent(this, TicketsActivity::class.java)
                intent.putExtra("User", user)
                startActivity(intent)
            }
            R.id.logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}