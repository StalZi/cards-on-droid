package com.stalzi.patientcards.cardsDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardsDao {
    // Get all cards in correct order
    @Query("SELECT * FROM cards ORDER BY position")
    suspend fun getCardsOrdered(): List<CardEntity>

    // Insert a new card and return its ID
    @Insert
    suspend fun insert(card: CardEntity): Long

    // Update a card
    @Update
    suspend fun update(card: CardEntity)

    // Delete a card
    @Delete
    suspend fun delete(card: CardEntity)

    @Query("""UPDATE cards SET position =
            CASE
                WHEN position = :sourcePos THEN :targetPos
                WHEN position = :targetPos THEN :sourcePos
            END
            WHERE position IN (:sourcePos, :targetPos)""")
    suspend fun swapPositions(sourcePos: Int, targetPos: Int)


    // Update image path for a specific card
    @Query("UPDATE cards SET imagePath = :imagePath WHERE id = :cardId")
    suspend fun updateImagePath(cardId: Long, imagePath: String)
}