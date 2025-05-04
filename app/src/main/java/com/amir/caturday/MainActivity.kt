package com.amir.caturday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.amir.caturday.domain.model.NavMenuItem
import com.amir.caturday.theme.AppTheme
import com.amir.caturday.theme.LocalNavigation
import com.amir.caturday.ui.BottomNavigationBar
import com.amir.caturday.ui.Navigation
import com.amir.caturday.ui.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<ActivityViewModel>()
            val state = viewModel.uiState.collectAsState()
            AppTheme(state.value.theme) {
                val nav = LocalNavigation.current
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = persistentListOf(
                                NavMenuItem(Screen.BreedsList.route, stringResource(R.string.nav_home), R.drawable.ic_paw),
                                NavMenuItem(Screen.FavoritesList.route, stringResource(R.string.nav_fav), R.drawable.ic_heart),
                                NavMenuItem(Screen.Settings.route, stringResource(R.string.nav_settings), R.drawable.ic_settings),
                            ),
                            onItemClick = {
                                viewModel.onTabClick(it.route)
                                nav.navigate(it.route) {
                                    launchSingleTop = true
                                    popUpTo(nav.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            },
                            selectedTab = state.value.selectedTab,
                        )
                    }) {
                    Box(modifier = Modifier.padding(it)) {
                        Navigation(navController = nav)
                    }
                }
            }
        }
    }
}