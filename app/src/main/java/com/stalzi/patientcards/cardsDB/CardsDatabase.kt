package com.stalzi.patientcards.cardsDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao

    companion object {
        // Singleton pattern to prevent multiple database instances
        @Volatile
        private var INSTANCE: CardsDatabase? = null

        fun getInstance(context: Context): CardsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardsDatabase::class.java,
                    "cards_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}