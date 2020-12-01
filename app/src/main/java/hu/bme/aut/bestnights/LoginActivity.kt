package hu.bme.aut.bestnights

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import hu.bme.aut.bestnights.data.UserDao
import hu.bme.aut.bestnights.data.UserDatabase
import hu.bme.aut.bestnights.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var database: UserDatabase
    private lateinit var db: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user-list").allowMainThreadQueries().build()
        db = database.userDao()

        var b = false
        for(user: User in db.getUsers()) {
            if(user.name == "admin"){
                b = true
            }
        }

        if(!b){
            val admin = User(null, "admin", "admin", "admin", null, null)
            db.insert(admin)
        }

        btnLogin.setOnClickListener {

            if (etUsername.text.toString().isEmpty()) {
                etUsername.requestFocus()
                etUsername.error = "Please enter your username."
            } else if (etPassword.text.toString().isEmpty()) {
                etPassword.requestFocus()
                etPassword.error = "Please enter your password."
            } else {
                val user =
                    db.getUser(
                        etUsername.text.toString().trim(),
                        etPassword.text.toString().trim()
                    )

                if (user != null) {
                    val intent = Intent(this, DecisionActivity::class.java)
                    intent.putExtra("User", user)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Wrong username or password, user not found.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            finish()
            startActivity(intent)
        }

    }
}