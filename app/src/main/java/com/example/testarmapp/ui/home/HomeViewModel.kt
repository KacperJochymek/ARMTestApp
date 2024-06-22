package com.example.testarmapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Witaj w aplikacji Test ARM!"
    }
    val text: LiveData<String> = _text
}