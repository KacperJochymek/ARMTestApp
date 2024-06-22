package com.example.testarmapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HardwarePropertiesManager
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class TestTemperatura : AppCompatActivity() {

    private lateinit var startStopButton: Button
    private lateinit var temperatureText: TextView
    private var isMonitoring = false
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000L // 1 sekunda (w milisekundach)

    //Runnable uzywamy do cyklicznego odczytu temperatury
    private val temperatureRunnable = object : Runnable {
        override fun run() {
            if (isMonitoring) {
                //wybór metody odczytu w zależności od wersji androida
                val temperature = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getCpuTemperatureApi26()
                } else {
                    getCpuTemperatureLegacy()
                }
                //aktualizujemy widoczek
                temperatureText.text = "Temperature: $temperature°C"
                handler.postDelayed(this, updateInterval)
            }
        }
    }


    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temperature)

        //inicjalizujemy zmienne z temperature
        startStopButton = findViewById(R.id.button_start_stop)
        temperatureText = findViewById(R.id.text_temperature)

        startStopButton.setOnClickListener {
            isMonitoring = !isMonitoring
            if (isMonitoring) {
                startStopButton.text = "STOP"
                handler.post(temperatureRunnable)
            } else {
                startStopButton.text = "START"
                handler.removeCallbacks(temperatureRunnable)
            }
        }
    }

    //Metoda odczytu temperatury z folderów,
    private fun getCpuTemperatureLegacy(): Float {
        val tempFilePaths = arrayOf(
            "/sys/class/thermal/thermal_zone0/temp",
            "/sys/class/thermal/thermal_zone1/temp",
            "/sys/class/hwmon/hwmon0/temp1_input"
        )
        for (path in tempFilePaths) {
            try {
                val tempFile = File(path)
                if (tempFile.exists()) {
                    val tempStr = tempFile.readText().trim()
                    val tempCelsius = tempStr.toFloat() / 1000.0f
                    return tempCelsius
                }
            } catch (e: Exception) {
                Log.e("TestTemperatura", "Błąd odczytu z $path", e)
            }
        }
        return -1.0f // Zwraca -1 w przypadku błędu
    }


    //Metoda odczytu dla versi API 26 i nowszych
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCpuTemperatureApi26(): Float {
        return try {
            val hardwarePropertiesManager =
                getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as? HardwarePropertiesManager
            val temperatures = hardwarePropertiesManager?.getDeviceTemperatures(
                HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU,
                HardwarePropertiesManager.TEMPERATURE_CURRENT
            )
            temperatures?.firstOrNull() ?: -1.0f // Zwraca -1 w przypadku błędu
        } catch (e: Exception) {
            Log.e("TestTemperatura", "Błąd odczytu CPU", e)
            -1.0f // Zwraca -1 w przypadku błędu
        }
    }
}
