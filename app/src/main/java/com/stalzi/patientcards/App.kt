package com.stalzi.patientcards

import android.app.Application
import android.content.Context
import com.stalzi.patientcards.cardsDB.CardsDatabase
import com.stalzi.patientcards.cardsDB.CardsRepository

class App : Application() {
    val database by lazy { CardsDatabase.getInstance(this) }
    val repository by lazy { CardsRepository(database.cardsDao()) }

}
val Context.app: App
    get() = applicationContext as App