package com.amir.caturday.ui

import androidx.navigation.NavBackStackEntry

sealed class Screen(
    val route: String,
) {
    companion object {
        const val ARG_ID = "arg_id"
    }

    data object BreedsList : Screen("breeds-list")

    data object Settings : Screen("settings")

    data object BreedsDetails : Screen("breeds-details?$ARG_ID={$ARG_ID}") {
        fun getId(navBackStackEntry: NavBackStackEntry): String? = navBackStackEntry.arguments?.getString(ARG_ID)

        fun withArg(id: String): String =
            route
                .replace("{$ARG_ID}", id)
    }
}
