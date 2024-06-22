package com.example.testarmapp

import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import kotlin.system.measureNanoTime

class AesTest : AppCompatActivity() {

    private lateinit var editTextInput: EditText
    private lateinit var buttonEncrypt: Button
    private lateinit var textEncrypted: TextView
    private lateinit var textDecrypted: TextView
    private lateinit var textTime: TextView

    private lateinit var secretKey: Key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.encryption)

        //Pobieramy z layoutu zmienne, ktore nas interesują
        editTextInput = findViewById(R.id.edit_text_input)
        buttonEncrypt = findViewById(R.id.button_encrypt)
        textEncrypted = findViewById(R.id.text_encrypted)
        textDecrypted = findViewById(R.id.text_decrypted)
        textTime = findViewById(R.id.text_time)

        // Generowanie klucza AES metoda
        secretKey = generateKey()

        buttonEncrypt.setOnClickListener { performEncryption() }
    }

    //Funkcja generująca klucz AES
    private fun generateKey(): Key {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    //Funkcja odpowiedzialna za szyfrowanie i odszyfrowanie
    private fun performEncryption() {
        val inputText = editTextInput.text.toString()

        if (inputText.isNotEmpty()) {
            try {
                var encryptedText: String
                var decryptedText: String
                //Mierzymy czas
                val totalTime = measureNanoTime {
                    encryptedText = encrypt(inputText, secretKey)
                    decryptedText = decrypt(encryptedText, secretKey)
                }

                val averageTime = totalTime

                //wyniki
                textEncrypted.text = "Zaszyfrowany tekst: $encryptedText"
                textDecrypted.text = "Odszyfrowany tekst: $decryptedText"
                textTime.text = "Czas wykonania: $averageTime ns"
            } catch (e: Exception) {
                textEncrypted.text = "Błąd: ${e.message}"
            }
        } else {
            textEncrypted.text = "Błąd: Wprowadź tekst"
        }
    }

    //Funkcja szyfrująca dane przy uzyciu AES
    private fun encrypt(data: String, key: Key): String {
        val cipher = Cipher.getInstance("AES")//Tworzymy instację klucza AES
        cipher.init(Cipher.ENCRYPT_MODE, key) //Inicjalizujemy generator z rozmiarem 256 bitów
        val encryptedBytes = cipher.doFinal(data.toByteArray())//szyfrujemy
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    //Funkcja odszyfrująca AES
    private fun decrypt(data: String, key: Key): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
