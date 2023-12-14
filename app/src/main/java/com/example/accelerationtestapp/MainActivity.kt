package com.example.accelerationtestapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var sManager: SensorManager
    private var magnetic = FloatArray(9)
    private var gravity = FloatArray(9)
    private var accrs = FloatArray(3)
    private var magf = FloatArray(3)
    private var values = FloatArray(3)

    private lateinit var btnSaveMeasurement: Button
    private lateinit var etMeasurementName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        val lRotation = findViewById<LinearLayout>(R.id.lRotation)
        btnSaveMeasurement = findViewById(R.id.btnSaveMeasurement)
        etMeasurementName = findViewById(R.id.etMeasurementName)

        sManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val sListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> accrs = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magf = event.values.clone()
                }

                SensorManager.getRotationMatrix(gravity, magnetic, accrs, magf)
                val outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    outGravity
                )
                SensorManager.getOrientation(outGravity, values)
                val degree = values[2] * 57.2958f
                val rotate = 270 + degree
                lRotation.rotation = rotate
                val rData = 90 + degree
                val color = if (rData.toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                lRotation.setBackgroundColor(color)
                tvSensor.text = rData.toInt().toString()
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }
        }

        sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListener, sensor2, SensorManager.SENSOR_DELAY_NORMAL)

        btnSaveMeasurement.setOnClickListener {
            val measurementName = etMeasurementName.text.toString()
            val angle = values[2] * 57.2958f + 90

            saveMeasurementData(measurementName, angle)

            val profileIntent = Intent()
            profileIntent.putExtra("measurementName", measurementName)
            profileIntent.putExtra("angle", angle)
            setResult(RESULT_OK, profileIntent)
            finish()
        }
    }

    private fun saveMeasurementData(measurementName: String, angle: Float) {
        val sharedPreferences = getSharedPreferences("MeasurementData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val currentCount = sharedPreferences.getInt("count", 0)
        editor.putString("name_$currentCount", measurementName)
        editor.putFloat("angle_$currentCount", angle)
        editor.putInt("count", currentCount + 1)

        editor.apply()
    }
}



