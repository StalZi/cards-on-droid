package com.stalzi.patientcards.cardsDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stalzi.patientcards.CardInfo

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val position: Int,
    val imagePath: String,
    val label: String
) {
    fun toCardInfo(): CardInfo {
        return CardInfo(
            image = imagePath.loadAsImage(), // You'll create this function
            label = label
        )
    }
}