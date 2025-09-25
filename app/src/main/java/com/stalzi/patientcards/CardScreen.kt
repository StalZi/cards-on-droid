package com.stalzi.patientcards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun CardScreen(image: Int, label: String) {
    Scaffold { innerPadding ->
        Text(
            modifier = Modifier
                    .padding(innerPadding),
            text = "$image $label",
            fontSize = 40.sp
        )
    }
}