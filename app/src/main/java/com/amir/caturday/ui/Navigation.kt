package com.amir.caturday.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amir.caturday.ActivityViewModel
import com.amir.caturday.domain.model.Theme

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.BreedsList.route
    ) {
        composable(route = Screen.BreedsList.route) {
        }
        composable(route = Screen.BreedsDetails.route, arguments = listOf(
            navArgument(Screen.ARG_ID) {
                type = NavType.StringType
                nullable = true
            }
        )) {
        }
        composable(route = Screen.FavoritesList.route) {
        }
        composable(route = Screen.Settings.route) {
            val viewModel = hiltViewModel<ActivityViewModel>()
            Button({
                viewModel.setTheme(Theme.entries.random())
            }) {
                Text("Change Theme")
            }
        }
    }
}