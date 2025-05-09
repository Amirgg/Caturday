package com.amir.caturday.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.caturday.domain.model.Theme
import com.amir.caturday.domain.usecase.settings.GetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel
    @Inject
    constructor(
        private val getThemeUseCase: GetThemeUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
        val uiState: StateFlow<State> = _uiState.asStateFlow()

        data class State(
            val selectedTab: String = Screen.BreedsList.route,
            val theme: Theme = Theme.DEFAULT_THEME,
        )

        init {
            updateTheme()
        }

        fun onTabClick(route: String) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(selectedTab = route)
                }
            }
        }

        private fun updateTheme() {
            viewModelScope.launch {
                getThemeUseCase().collectLatest { theme ->
                    _uiState.update {
                        it.copy(theme = theme)
                    }
                }
            }
        }
    }
