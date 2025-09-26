package com.stalzi.patientcards

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _cardsList = mutableStateListOf<CardInfo>()
    val cardsList: List<CardInfo> = _cardsList

    fun addCard() {
        _cardsList.add(CardInfo(R.drawable.default_no_image, "Comp ${cardsList.size}"))
        println(cardsList)
    }

    fun swapCards(source: Int, target: Int) {
        println(_cardsList)

        _cardsList[source] = _cardsList[target].also {
            _cardsList[target] = _cardsList[source]
        }

        println(_cardsList)
    }
}