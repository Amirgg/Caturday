package com.amir.caturday.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.ui.settings.components.CeilingLight

@Composable
fun SettingsScreen() {
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val state = settingsViewModel.uiState.collectAsStateWithLifecycle()
    CeilingLight(
        data = state.value.isLight,
        onThemeChange = settingsViewModel::onThemeChange,
    )
}

@Composable
@PreviewLightDark
private fun SettingsScreenPreview() {
    AppPreviewTheme {
        SettingsScreen()
    }
}
