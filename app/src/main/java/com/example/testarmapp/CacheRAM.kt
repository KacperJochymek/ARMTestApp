package com.example.testarmapp

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CacheRAM : AppCompatActivity() {

    private lateinit var testMemoryButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cacheram)


        //Inicjalizacja widoków z cacheram
        testMemoryButton = findViewById(R.id.button_test_memory)
        resultText = findViewById(R.id.text_memory_result)

        //Ustawienie akcji dla przycisku
        testMemoryButton.setOnClickListener {
            val startTime = SystemClock.elapsedRealtime()

            val result = testMemoryOperations()

            val endTime = SystemClock.elapsedRealtime()
            val elapsedTime = endTime - startTime

            resultText.text = "Wynik: $result\nCzas wykonania: $elapsedTime ms"
        }
    }

    // Test odczytu i zapisu do pamięci RAM, tablica 100 000
    private fun testMemoryOperations(): String {
        val data = IntArray(100_000) { it }

        // Obliczenia na tablicy
        for (i in data.indices) {
            data[i] = data[i] * 2
        }
        val dataCorrect = data.all { it % 2 == 0 }

        // Zwraca wynik, czy zapis i odczyt jest prawidlowy
        return if (dataCorrect) {
            "Odczyt i zapis do pamięci RAM wykonane poprawnie."
        } else {
            "Błąd w odczycie i zapisie."
        }
    }
}

