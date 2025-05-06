package com.amir.caturday.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.theme.LocalNavigation
import com.amir.caturday.ui.Screen
import com.amir.caturday.ui.list.components.BreedsList
import com.amir.caturday.ui.list.components.EmptyPage
import com.amir.caturday.ui.list.components.SearchRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsListScreen() {
    val breedsListViewModel = hiltViewModel<BreedsListViewModel>()
    val state = breedsListViewModel.uiState.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()
    val navigation = LocalNavigation.current

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        PullToRefreshBox(
            isRefreshing = state.value.isLoading && state.value.breeds.isEmpty(),
            onRefresh = breedsListViewModel::refresh,
            state = pullToRefreshState,
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Column {
                SearchRow(
                    onSearchQueryChanged = breedsListViewModel::onSearchQueryChanged,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                )
                when {
                    state.value.showNoResultPage ->
                        EmptyPage(
                            title = stringResource(R.string.no_result_title),
                            message = stringResource(R.string.no_result_desc),
                            modifier = Modifier.weight(1F),
                            icon = R.drawable.ic_search,
                            showRetry = false,
                        )

                    state.value.showFullPageError ->
                        EmptyPage(
                            title = stringResource(R.string.error_title),
                            message = state.value.errorMessage.ifEmpty { stringResource(R.string.error_desc) },
                            modifier = Modifier.weight(1F),
                            icon = R.drawable.ic_search,
                            showRetry = true,
                            onRetryClick = breedsListViewModel::onRetryClick,
                        )

                    else ->
                        BreedsList(
                            state = state.value,
                            onFavoriteClick = breedsListViewModel::onFavoriteClick,
                            onBreedClick = { navigation.navigate(Screen.BreedsDetails.withArg(it.id)) },
                            onPaginate = breedsListViewModel::onPaginate,
                            onDismissError = breedsListViewModel::onDismissErrorClick,
                            onRetryClick = breedsListViewModel::onRetryClick,
                            modifier = Modifier.weight(1F),
                        )
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun BreedsListScreenPreview() {
    AppPreviewTheme {
        BreedsListScreen()
    }
}
