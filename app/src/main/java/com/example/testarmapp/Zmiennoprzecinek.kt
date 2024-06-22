package com.example.testarmapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.measureNanoTime

class Zmiennoprzecinek : AppCompatActivity() {

    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonSubtract: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var resultTextView: TextView
    private lateinit var timeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zmiennoprzecinek)

        // Inicjalizacja elementów interfejsu użytkownika
        editTextNumber1 = findViewById(R.id.edit_text_number1)
        editTextNumber2 = findViewById(R.id.edit_text_number2)
        buttonAdd = findViewById(R.id.button_add)
        buttonSubtract = findViewById(R.id.button_subtract)
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonDivide = findViewById(R.id.button_divide)
        resultTextView = findViewById(R.id.text_result)
        timeTextView = findViewById(R.id.text_time)

        buttonAdd.setOnClickListener { performOperation("+") }
        buttonSubtract.setOnClickListener { performOperation("-") }
        buttonMultiply.setOnClickListener { performOperation("*") }
        buttonDivide.setOnClickListener { performOperation("/") }
    }

    // Metoda do wykonania operacji matematycznych na podstawie wybranego operatora
    private fun performOperation(operator: String) {
        val number1Str = editTextNumber1.text.toString()
        val number2Str = editTextNumber2.text.toString()

        if (number1Str.isNotEmpty() && number2Str.isNotEmpty()) {
            try {
                val number1 = number1Str.toDouble()
                val number2 = number2Str.toDouble()
                var result = 0.0

                val totalTime = measureNanoTime {
                    result = when (operator) {
                        "+" -> number1 + number2
                        "-" -> number1 - number2
                        "*" -> number1 * number2
                        "/" -> number1 / number2
                        else -> throw IllegalArgumentException("Nieznany operator")
                    }
                }
                // Zwrócenie wyników
                resultTextView.text = "Wynik: $result"
                timeTextView.text = "Czas wykonania: $totalTime ns"
            } catch (e: NumberFormatException) {
                resultTextView.text = "Błąd: Niepoprawne liczby"
            } catch (e: IllegalArgumentException) {
                resultTextView.text = "Błąd: Nieznany operator"
            }
        } else {
            resultTextView.text = "Błąd: Wprowadź obie liczby"
        }
    }
}
