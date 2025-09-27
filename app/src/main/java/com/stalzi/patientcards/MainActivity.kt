package com.stalzi.patientcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Serializable
object MainRoute
@Serializable
data class CardRoute(val image: Int, val label: String)

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = MainRoute
            ) {
                composable<MainRoute> {
                    MainScreen(
                        viewModel,
                        onNavigateToCard = { image, label ->
                            // Navigate using the serializable route object
                            navController.navigate(CardRoute(image, label))
                        }
                    )
                }

                composable<CardRoute> { backStackEntry ->
                    // Extract the route object with type safety
                    val cardRoute = backStackEntry.toRoute<CardRoute>()
                    CardScreen(
                        image = cardRoute.image,
                        label = cardRoute.label
                    )
                }
            }
        }
    }
}
