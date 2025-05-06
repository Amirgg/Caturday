package com.amir.caturday.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.caturday.data.repo.BreedsRepositoryImpl
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.usecase.breed.GetBreedsUseCase
import com.amir.caturday.domain.usecase.breed.InvalidateCacheUseCase
import com.amir.caturday.domain.usecase.breed.PaginateBreedsUseCase
import com.amir.caturday.domain.usecase.breed.ToggleFavoriteUseCase
import com.amir.caturday.ui.list.model.BreedCardUiModel
import com.amir.caturday.ui.list.model.toBreedCardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BreedsListViewModel
    @Inject
    constructor(
        private val getBreedsUseCase: GetBreedsUseCase,
        private val paginateBreedsUseCase: PaginateBreedsUseCase,
        private val invalidateCacheUseCase: InvalidateCacheUseCase,
        private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
        val uiState: StateFlow<State> = _uiState.asStateFlow()
        private val searchQuery = MutableStateFlow("")
        private var errorMessageJob: Job? = null

        data class State(
            val breeds: ImmutableList<BreedCardUiModel> = persistentListOf(),
            val searchingBreeds: ImmutableList<BreedCardUiModel> = persistentListOf(),
            val hasMore: Boolean = true,
            val isSearching: Boolean = false,
            val isLoading: Boolean = false,
            val errorMessage: String = "",
            val showError: Boolean = false,
            val paginationError: Boolean = false,
        ) {
            val showNoResultPage = isSearching && searchingBreeds.isEmpty()
            val showFullPageError = showError && breeds.isEmpty()
            val showPaginationLoading = hasMore && isSearching.not()
        }

        init {
            observeBreeds()
            handleSearch()
        }

        fun onPaginate() {
            if (uiState.value.isLoading) return
            viewModelScope.launch {
                paginateBreedsUseCase(
                    page = uiState.value.breeds.size / BreedsRepositoryImpl.LIMIT,
                ).collect { result ->
                    when (result) {
                        is DataState.Failure -> handleError(result.message)
                        is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is DataState.Success -> _uiState.update { it.copy(isLoading = false, paginationError = false) }
                    }
                }
            }
        }

        fun refresh() {
            viewModelScope.launch(Dispatchers.IO) {
                invalidateCacheUseCase()
            }
        }

        fun onToggleFavorite(breed: BreedCardUiModel) {
            viewModelScope.launch {
                toggleFavoriteUseCase(breed.id, breed.isFavorite.not())
            }
        }

        fun onRetryClick() {
            _uiState.update {
                it.copy(showError = false)
            }
            onPaginate()
        }

        fun onDismissErrorClick() {
            errorMessageJob?.cancel()
            _uiState.update {
                it.copy(showError = false)
            }
        }

        fun onSearchQueryChanged(query: String) {
            searchQuery.value = query
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            if (query.isBlank()) {
                _uiState.update {
                    it.copy(
                        isSearching = false,
                    )
                }
            }
        }

        private fun observeBreeds() {
            viewModelScope.launch {
                getBreedsUseCase().collect { result ->
                    when (result) {
                        is DataState.Failure -> handleError(result.message)
                        is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is DataState.Success -> {
                            val hasMore = uiState.value.breeds.size != result.data.size || uiState.value.breeds.isEmpty()
                            _uiState.update {
                                it.copy(
                                    breeds = result.data.map { it.toBreedCardUiModel() }.toImmutableList(),
                                    isLoading = false,
                                    hasMore = hasMore,
                                    paginationError = false,
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun handleSearch() {
            viewModelScope.launch {
                searchQuery
                    .debounce(700)
                    .distinctUntilChanged()
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .flatMapLatest { query ->
                        flowOf(uiState.value.breeds.filter { it.name.lowercase().contains(query.lowercase()) })
                    }.collect { result ->
                        _uiState.update {
                            it.copy(
                                searchingBreeds = result.toImmutableList(),
                                isSearching = true,
                            )
                        }
                    }
            }
        }

        private fun handleError(message: String) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    paginationError = it.breeds.isNotEmpty(),
                )
            }
            if (uiState.value.breeds.isEmpty()) {
                _uiState.update {
                    it.copy(
                        errorMessage = message,
                        showError = true,
                    )
                }
            } else {
                errorMessageJob?.cancel()
                _uiState.update {
                    it.copy(errorMessage = message, showError = true)
                }
                errorMessageJob =
                    viewModelScope.launch {
                        delay(3_000)
                        _uiState.update {
                            it.copy(showError = false)
                        }
                    }
            }
        }
    }
