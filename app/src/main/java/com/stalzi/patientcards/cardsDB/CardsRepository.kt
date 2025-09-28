package com.stalzi.patientcards.cardsDB

import com.stalzi.patientcards.CardInfo

class CardsRepository(private val cardsDao: CardsDao) {

    suspend fun getCardsOrdered(): List<CardInfo> {
        return cardsDao.getCardsOrdered().map { it.toCardInfo() }
    }

    suspend fun addCard(card: CardInfo, pos: Int) {
        val newCard = CardEntity(
            label = card.label,
            imagePath = "",
            position = pos
        )
        cardsDao.insert(newCard)
    }

    suspend fun swapCards(source: Int, target: Int) {
        cardsDao.swapPositions(source, target)
    }

//    // Delete a card
//    suspend fun deleteCard(cardId: Long) {
//        // We'll implement this with proper card retrieval later
//    }
}