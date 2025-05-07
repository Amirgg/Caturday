package com.amir.caturday.ui.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amir.caturday.domain.model.Theme
import com.amir.caturday.ui.details.BreedsDetailsScreen
import com.amir.caturday.ui.list.BreedsListScreen

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
            val viewModel = hiltViewModel<ActivityViewModel>()
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Button({
                    viewModel.setTheme(Theme.entries.random())
                }) {
                    Text("Change Theme")
                }
            }
        }
    }
}
