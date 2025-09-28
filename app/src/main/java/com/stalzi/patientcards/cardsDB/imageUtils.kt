package com.stalzi.patientcards.cardsDB

import com.stalzi.patientcards.R

fun String.loadAsImage(): Int {
    return if (this.isNotEmpty()) {
        0
    } else {
        R.drawable.default_no_image
    }
}