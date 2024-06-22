package com.example.testarmapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PoprawnoscProcesora : AppCompatActivity() {

    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var buttonCheck: Button
    private lateinit var checkBoxLogic: CheckBox
    private lateinit var resultTextView: TextView
    private lateinit var detailedResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poprawnosc)

        // inicjalizujemy widoki z poprawnosc
        editTextNumber1 = findViewById(R.id.edit_text_number1)
        editTextNumber2 = findViewById(R.id.edit_text_number2)
        buttonCheck = findViewById(R.id.button_check)
        checkBoxLogic = findViewById(R.id.check_box_logic)
        resultTextView = findViewById(R.id.text_result)
        detailedResultTextView = findViewById(R.id.text_detailed_result)


        //Pobranie danych z pól tekstowych i wyrzucenie wyjątków
        buttonCheck.setOnClickListener {
            val number1 = try { editTextNumber1.text.toString().toInt() } catch (e: NumberFormatException) { 0 }
            val number2 = try { editTextNumber2.text.toString().toInt() } catch (e: NumberFormatException) { 0 }
            val testLogic = checkBoxLogic.isChecked


            //Spr czy nie zerowe
            if (number1 == 0 || number2 == 0) {
                resultTextView.text = "Podano nieprawidłowe dane"
                detailedResultTextView.text = ""
            } else {
                val processor = ProcessorChecker()
                val results = processor.sprawdzPoprawnosc(number1, number2, testLogic)

                resultTextView.text = results.firstOrNull() ?: "Brak wyniku"
                val detailedResults = results.drop(1).joinToString("\n") { it ?: "" }
                detailedResultTextView.text = detailedResults
            }
        }
    }

    inner class ProcessorChecker {

        fun testOperacjeArytmetyczne(a: Int, b: Int): List<String> {
            val results = mutableListOf<String>()

            //Wykonanie wyników i dodanie do listy wyswietlania
            val suma = a + b
            val roznica = a - b
            val iloczyn = a * b
            val iloraz = if (b != 0) a / b else "Błąd: dzielenie przez zero"
            val modulo = if (b != 0) a % b else "Błąd: dzielenie przez zero"

            results.add("Suma: $suma / ${if (suma == a + b) "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Różnica: $roznica / ${if (roznica == a - b) "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Iloczyn: $iloczyn / ${if (iloczyn == a * b) "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Iloraz: $igitloraz / ${if (iloraz == if (b != 0) a / b else "Błąd: dzielenie przez zero") "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Modulo: $modulo / ${if (modulo == if (b != 0) a % b else "Błąd: dzielenie przez zero") "Wynik poprawny" else "Wynik niepoprawny"}")

            return results
        }

        //obliczenia logiczne
        fun testOperacjeLogiczne(p: Boolean, q: Boolean): List<String> {
            val results = mutableListOf<String>()

            val andOperacja = p && q
            val orOperacja = p || q
            val notOperacja = !p

            results.add("Operacja AND: $andOperacja / ${if (andOperacja == (p && q)) "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Operacja OR: $orOperacja / ${if (orOperacja == (p || q)) "Wynik poprawny" else "Wynik niepoprawny"}")
            results.add("Operacja NOT: $notOperacja / ${if (notOperacja == !p) "Wynik poprawny" else "Wynik niepoprawny"}")

            return results
        }

        //metoda ktora laczy testy
        fun sprawdzPoprawnosc(a: Int, b: Int, testLogic: Boolean): List<String> {
            val results = mutableListOf<String>()

            results.add("Wynik testu arytmetycznego:")
            val wynikArytmetyczne = testOperacjeArytmetyczne(a, b)
            results.addAll(wynikArytmetyczne)

            if (testLogic) {
                results.add("Wynik testu logicznego:")
                val wynikLogiczne = testOperacjeLogiczne(a != 0, b != 0)
                results.addAll(wynikLogiczne)
            }

            return results
        }
    }
}
