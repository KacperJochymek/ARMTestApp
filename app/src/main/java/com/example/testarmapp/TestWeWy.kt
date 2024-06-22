package com.example.testarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestWeWy : AppCompatActivity() {

    private lateinit var usbStatusTextView: TextView
    private lateinit var testResultTextView: TextView
    private lateinit var usbManager: UsbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wewy)


        // Inicjalizacja elementów interfejsu użytkownika
        usbStatusTextView = findViewById(R.id.text_usb_status)
        testResultTextView = findViewById(R.id.text_test_result)
        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager

        // Inicjalizacja przycisku testowania połączenia USB
        val testButton: Button = findViewById(R.id.button_test)
        testButton.setOnClickListener {
            testUSBConnection()
        }

        // Rejestracja odbiornika broadcast
        val filter = IntentFilter().apply {
            addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        }
        registerReceiver(usbReceiver, filter)
    }

    // Odbiornik broadcast do nasłuchiwania zmian w statusie USB
    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val action = it.action
                when (action) {
                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                        usbStatusTextView.text = "Podłączono urządzenie USB"
                        testResultTextView.text = ""
                    }
                    UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                        usbStatusTextView.text = ""
                        testResultTextView.text = "Urządzenie USB zostało odłączone"
                    }
                }
            }
        }
    }
// Metoda do przeprowadzenia testu połączenia USB
    private fun testUSBConnection() {
        val deviceList = usbManager.deviceList
        testResultTextView.text = if (deviceList.isNotEmpty()) {
            "Port USB działa poprawnie - wykryto urządzenie"
        } else {
            "Port USB działa poprawnie - brak urządzenia"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(usbReceiver)
    }
}
