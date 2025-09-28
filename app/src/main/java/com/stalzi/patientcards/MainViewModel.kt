package com.stalzi.patientcards

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalzi.patientcards.cardsDB.CardsRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CardsRepository
): ViewModel() {
    private val _cardsList = mutableStateListOf<CardInfo>()
    val cardsList: List<CardInfo> = _cardsList

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            _cardsList.addAll(repository.getCardsOrdered())
        }
    }

    fun addCard() {
        val pos = _cardsList.size
        val newCard = CardInfo(R.drawable.default_no_image, "Comp $pos")
        _cardsList.add(newCard)
        viewModelScope.launch {
            repository.addCard(newCard, pos)
        }
        println(cardsList)
    }

    fun swapCards(source: Int, target: Int) {
        println(_cardsList)

        _cardsList[source] = _cardsList[target].also {
            _cardsList[target] = _cardsList[source]
        }
        try {
            println("swapping cards")
            viewModelScope.launch {
                repository.swapCards(source, target)
            }
        } catch (e: Exception) {
            println("DB swap failed, changing UI back..\n$e")
            _cardsList[source] = _cardsList[target].also {
                _cardsList[target] = _cardsList[source]
            }
        }

        println(_cardsList)
    }
}