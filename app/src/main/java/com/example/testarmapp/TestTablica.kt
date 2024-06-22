package com.example.testarmapp

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestTablica : AppCompatActivity() {

    private lateinit var testMemoryButton: Button
    private lateinit var testSortingButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

        //Inicjalizacja przycików z zapisu
        testMemoryButton = findViewById(R.id.button_test_memory)
        testSortingButton = findViewById(R.id.button_test_sorting)
        resultText = findViewById(R.id.text_memory_result)

        testMemoryButton.setOnClickListener {
            testProcessorOperations("Test CPU: Liczenie liczb pierwszych")
        }

        testSortingButton.setOnClickListener {
            testProcessorOperations("Test CPU: Sortowanie")
        }
    }

    //metoda wykonująca operacje testowe na procesorze
    private fun testProcessorOperations(testType: String) {
        val startTime = SystemClock.elapsedRealtime()

        val result = when (testType) {
            "Test CPU: Liczenie liczb pierwszych" -> countPrimeNumbers()
            "Test CPU: Sortowanie" -> performSorting()
            else -> "Nieznany test"
        }

        val endTime = SystemClock.elapsedRealtime()
        val elapsedTime = endTime - startTime

        resultText.text = "Wynik: $result\nCzas wykonania: $elapsedTime ms"
    }

    //metoda, ktora bombi procka i uzyskuje liczby, ktore sa pierwsze (przy czym sprawdza czas)10 000 000
    private fun countPrimeNumbers(): String {
        val maxIterations = 10_000_000
        var primeCount = 0

        for (i in 2..maxIterations) {
            if (isPrime(i)) {
                primeCount++
            }
        }

        return "Liczba znalezionych liczb pierwszych: $primeCount"
    }

    //metoda, ktora również bombi procka i mierzy czas wykonania tablicy 100 000
    private fun performSorting(): String {
        val arraySize = 100_000
        val array = IntArray(arraySize) { it }

        array.shuffle() // mieszanie tablicy

        val startTime = System.currentTimeMillis()
        array.sort()
        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - startTime

        return "Czas sortowania $arraySize elementowej tablicy: $elapsedTime ms"
    }

    //Metoda sprawdzająca czy liczba jest liczba pierwsza
    private fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        for (i in 2..Math.sqrt(number.toDouble()).toInt()) {
            if (number % i == 0) return false
        }
        return true
    }
}
