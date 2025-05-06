package com.amir.caturday.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.domain.model.NavMenuItem
import com.amir.caturday.theme.AppPreviewTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BottomNavigationBar(
    items: ImmutableList<NavMenuItem>,
    selectedTab: String,
    onItemClick: (NavMenuItem) -> Unit,
) {
    Column {
        Box(
            modifier =
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
        )
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surfaceBright,
        ) {
            items.forEach {
                val selected = selectedTab == it.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        onItemClick(it)
                    },
                    colors =
                        NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                            disabledIconColor = MaterialTheme.colorScheme.outline,
                            disabledTextColor = MaterialTheme.colorScheme.outline,
                        ),
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )
                    },
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(top = 2.dp),
                        ) {
                            Icon(
                                painter = painterResource(it.iconResource),
                                contentDescription = it.name,
                                modifier =
                                    Modifier
                                        .size(24.dp),
                            )
                        }
                    },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun BottomNavigationBarPreview() {
    AppPreviewTheme {
        BottomNavigationBar(
            items =
                persistentListOf(
                    NavMenuItem("R1", stringResource(R.string.nav_home), R.drawable.ic_paw),
                    NavMenuItem("R3", stringResource(R.string.nav_settings), R.drawable.ic_settings),
                ),
            selectedTab = "R1",
            onItemClick = {},
        )
    }
}
