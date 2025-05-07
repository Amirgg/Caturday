package com.amir.caturday.ui.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.data.breedDtoList
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.ui.list.BreedsListViewModel
import com.amir.caturday.ui.list.model.BreedCardUiModel
import com.amir.caturday.ui.list.model.toBreedCardUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun BreedsList(
    state: BreedsListViewModel.State,
    onFavoriteClick: (breed: BreedCardUiModel) -> Unit,
    onBreedClick: (breed: BreedCardUiModel) -> Unit,
    onPaginate: () -> Unit,
    onDismissError: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val data by remember(state.isSearching, state.breeds, state.searchingBreeds) {
        mutableStateOf(
            if (state.isSearching) {
                state.searchingBreeds
            } else {
                state.breeds
            },
        )
    }
    Box(
        modifier =
        modifier,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
        ) {
            item { /*top padding*/ }
            items(
                count = data.size,
                key = { data[it].id },
                contentType = { 1 },
            ) {
                BreedCard(
                    data = data[it],
                    onClick = { onBreedClick(data[it]) },
                    onFavoriteClick = { onFavoriteClick(data[it]) },
                )
            }

            if (state.paginationError) {
                item {
                    RetryButton(
                        onClick = onRetryClick,
                        modifier =
                            Modifier
                                .padding(10.dp),
                    )
                }
            } else if (state.showPaginationLoading) {
                item {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .padding(30.dp)
                                .onGloballyPositioned {
                                    onPaginate()
                                },
                    )
                }
            }
            item {}
        }
        AnimatedVisibility(state.showError, modifier = Modifier.align(Alignment.BottomCenter)) {
            ErrorRow(
                data = state.errorMessage,
                onDismissClick = onDismissError,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun BreedsListPreview() {
    AppPreviewTheme {
        BreedsList(
            state =
                BreedsListViewModel.State(
                    breeds = breedDtoList.map { it.toBreed().toBreedCardUiModel() }.toImmutableList(),
                    searchingBreeds = persistentListOf(),
                    hasMore = true,
                    isSearching = false,
                    isLoading = false,
                    errorMessage = "",
                    showError = false,
                ),
            onFavoriteClick = {},
            onBreedClick = {},
            onPaginate = {},
            onDismissError = {},
            onRetryClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
