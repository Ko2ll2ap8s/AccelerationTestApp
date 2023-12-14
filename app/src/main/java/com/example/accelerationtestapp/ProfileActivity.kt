package com.example.accelerationtestapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvPassword: TextView
    //private lateinit var etWord: EditText
    private lateinit var btnMeasure: Button
    private lateinit var llMeasurements: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        tvUsername = findViewById(R.id.tvUsername)
        tvPassword = findViewById(R.id.tvPassword)
        //etWord = findViewById(R.id.etWord)
        btnMeasure = findViewById(R.id.btnMeasure)
        llMeasurements = findViewById(R.id.llMeasurements)

        tvUsername.text = "Username: $username"
        tvPassword.text = "Password: $password"

            btnMeasure.setOnClickListener {
            //val wordToMeasure = etWord.text.toString()

            val measureIntent = Intent(this, MainActivity::class.java)
            //measureIntent.putExtra("wordToMeasure", wordToMeasure)
            startActivityForResult(measureIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val measurementName = data?.getStringExtra("measurementName")
            val angle = data?.getFloatExtra("angle", 0f)

            val textView = TextView(this)
            textView.text = "Measurement: $measurementName, Angle: $angle"
            llMeasurements.addView(textView)
        }
    }
}





