package com.amir.caturday.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.ui.details.component.BreedDetails
import com.amir.caturday.ui.list.components.EmptyPage

@Composable
fun BreedsDetailsScreen() {
    val breedsDetailsViewModel = hiltViewModel<BreedsDetailsViewModel>()
    val state = breedsDetailsViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            state.value.isLoading ->
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .align(Alignment.Center)
                            .padding(20.dp),
                )

            state.value.errorMessage.isNotBlank() ->
                EmptyPage(
                    title = stringResource(R.string.error_title),
                    message = state.value.errorMessage.ifEmpty { stringResource(R.string.error_desc) },
                    modifier = Modifier.fillMaxSize(),
                    icon = R.drawable.ic_paw,
                    showRetry = false,
                )

            else ->
                state.value.breed?.let {
                    BreedDetails(data = it, onFavoriteClick = {
                        breedsDetailsViewModel.onFavoriteClick(it)
                    })
                }
        }
    }
}

@Composable
@PreviewLightDark
private fun BreedsDetailsScreenPreview() {
    AppPreviewTheme {
        BreedsDetailsScreen()
    }
}
