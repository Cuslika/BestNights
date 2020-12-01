package hu.bme.aut.bestnights

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_decision.*

class DecisionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val FESTIVAL = 1
        const val PARTY = 2
    }

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decision)

        user = intent.getSerializableExtra("User") as User

        setTitle("Choose one")

        var nv: NavigationView = findViewById(R.id.navView)
        var nh: View = nv.getHeaderView(0)
        var ntv: TextView = nh.findViewById(R.id.yourName)
        ntv.setText(user.name)

        if(user.name.equals("admin")) {
            nv.menu.clear()
            nv.inflateMenu(R.menu.nav_drawer_admin_decision_menu)
        }

        drawer = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        nv.setNavigationItemSelectedListener(this)
        nv.bringToFront()

        btnFestival.setOnClickListener {
            if(user.name.equals("admin")){
                val intent = Intent(this, FestivalListActivityAdmin::class.java)
                intent.putExtra("User", user)
                startActivity(intent)
            } else {
                val intent = Intent(this, FestivalListActivity::class.java)
                intent.putExtra("User", user)
                finish()
                startActivity(intent)
            }
        }

        btnParty.setOnClickListener {
            if(user.name.equals("admin")){
                val intent = Intent(this, PartyListActivityAdmin::class.java)
                intent.putExtra("User", user)
                startActivity(intent)
            } else {
                val intent = Intent(this, PartyListActivity::class.java)
                intent.putExtra("User", user)
                finish()
                startActivity(intent)
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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