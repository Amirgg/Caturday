package com.amir.caturday.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.usecase.breed.GetBreedByIdUseCase
import com.amir.caturday.domain.usecase.breed.ToggleFavoriteUseCase
import com.amir.caturday.ui.details.model.BreedDetailsUiModel
import com.amir.caturday.ui.details.model.toBreedDetailsUiModel
import com.amir.caturday.ui.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsDetailsViewModel
    @Inject
    constructor(
        private val getBreedByIdUseCase: GetBreedByIdUseCase,
        private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
        val uiState: StateFlow<State> = _uiState.asStateFlow()

        data class State(
            val breed: BreedDetailsUiModel? = null,
            val isLoading: Boolean = false,
            val errorMessage: String = "",
        )

        init {
            savedStateHandle.get<String>(Screen.ARG_ID)?.let {
                getBreed(it)
            }
        }

        fun onFavoriteClick(breed: BreedDetailsUiModel) {
            viewModelScope.launch {
                toggleFavoriteUseCase(breed.id, breed.isFavorite.not())
            }
        }

        private fun getBreed(id: String) {
            viewModelScope.launch {
                getBreedByIdUseCase(id).collect { result ->
                    when (result) {
                        is DataState.Failure -> _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                        is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is DataState.Success -> _uiState.update { it.copy(isLoading = false, breed = result.data.toBreedDetailsUiModel()) }
                    }
                }
            }
        }
    }
