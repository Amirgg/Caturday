package com.amir.caturday.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
        }
    }
}