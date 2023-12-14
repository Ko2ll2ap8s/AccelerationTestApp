package com.example.accelerationtestapp// com.example.accelerationtestapp.RegistrationActivity.kt
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


import com.example.accelerationtestapp.R

 class RegistrationActivity : AppCompatActivity() {

    private val sharedPreferences by lazy {
        getSharedPreferences("UserData", Context.MODE_PRIVATE)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)

            registerUser(username, password)
            finish()
        }
    }

    private fun registerUser(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }
 }


