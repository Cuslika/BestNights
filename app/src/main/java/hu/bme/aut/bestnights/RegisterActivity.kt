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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.etPassword
import kotlinx.android.synthetic.main.activity_register.etUsername

class RegisterActivity : AppCompatActivity() {

    private lateinit var db: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user-list").allowMainThreadQueries().build().userDao()

        btnRegister.setOnClickListener {
            var uN = etUsername.text.toString().trim()
            var uE = etEmail.text.toString().trim()
            var pw = etPassword.text.toString().trim()
            var pwa = etPasswordA.text.toString().trim()

            if (etUsername.text.toString().isEmpty()) {
                etUsername.requestFocus()
                etUsername.error = "Please enter your username."
            } else if (etEmail.text.toString().isEmpty()) {
                etEmail.requestFocus()
                etEmail.error = "Please enter your email."
            }else if (etPassword.text.toString().isEmpty()) {
                etPassword.requestFocus()
                etPassword.error = "Please enter your password."
            }else if (etPasswordA.text.toString().isEmpty()) {
                etPasswordA.requestFocus()
                etPasswordA.error = "Please enter your password again."
            }
            if(pw.equals(pwa)) {
                val user = User(null, uN, uE, pw, null, null)
                if(newUserCheck(user)){
                    db.insert(user)
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Change name or email address!",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Passwords are not matching!",Toast.LENGTH_LONG).show()
            }
        }

        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun newUserCheck(user: User): Boolean {
        var uL = db.getUsers()
        for(u: User in uL) {
            if(u.name == user.name || u.email == user.email)
                return false
        }
        return true
    }

}