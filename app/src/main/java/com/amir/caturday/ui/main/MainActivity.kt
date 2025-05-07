package com.amir.caturday.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.amir.caturday.R
import com.amir.caturday.domain.model.NavMenuItem
import com.amir.caturday.theme.AppTheme
import com.amir.caturday.theme.LocalNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<ActivityViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            AppTheme(state.value.theme) {
                val nav = LocalNavigation.current
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items =
                                persistentListOf(
                                    NavMenuItem(Screen.BreedsList.route, stringResource(R.string.nav_home), R.drawable.ic_paw),
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
                    },
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Navigation(navController = nav)
                    }
                }
            }
        }
    }
}
