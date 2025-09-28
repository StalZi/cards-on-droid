package com.stalzi.patientcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stalzi.patientcards.cardsDB.CardsRepository

class MainViewModelFactory(private val repository: CardsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}