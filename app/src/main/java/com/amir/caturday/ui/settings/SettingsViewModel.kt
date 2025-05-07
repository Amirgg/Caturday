package com.amir.caturday.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.caturday.domain.model.Theme
import com.amir.caturday.domain.usecase.settings.GetThemeUseCase
import com.amir.caturday.domain.usecase.settings.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val setThemeUseCase: SetThemeUseCase,
        private val getThemeUseCase: GetThemeUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
        val uiState: StateFlow<State> = _uiState.asStateFlow()

        data class State(
            val theme: Theme = Theme.DEFAULT_THEME,
        ) {
            val isLight = theme.value == Theme.LIGHT_GREEN.value
        }

        init {
            getTheme()
        }

        fun onThemeChange() {
            viewModelScope.launch {
                val theme =
                    if (uiState.value.isLight) {
                        Theme.DARK_YELLOW
                    } else {
                        Theme.LIGHT_GREEN
                    }
                setThemeUseCase(theme)
            }
        }

        private fun getTheme() {
            viewModelScope.launch {
                getThemeUseCase().collectLatest { theme ->
                    _uiState.update { it.copy(theme = theme) }
                }
            }
        }
    }
