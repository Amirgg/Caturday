package com.amir.caturday.ui.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amir.caturday.ui.details.BreedsDetailsScreen
import com.amir.caturday.ui.list.BreedsListScreen
import com.amir.caturday.ui.settings.SettingsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.BreedsList.route,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(route = Screen.BreedsList.route) {
            BreedsListScreen()
        }
        composable(
            route = Screen.BreedsDetails.route,
            arguments =
                listOf(
                    navArgument(Screen.ARG_ID) {
                        type = NavType.StringType
                        nullable = true
                    },
                ),
        ) {
            BreedsDetailsScreen()
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
