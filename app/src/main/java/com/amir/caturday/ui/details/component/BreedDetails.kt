package com.amir.caturday.ui.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.theme.Grey500
import com.amir.caturday.theme.Red500
import com.amir.caturday.ui.details.model.BreedDetailsUiModel
import com.amir.caturday.ui.details.model.toBreedDetailsUiModel
import com.amir.caturday.ui.list.components.Badge
import com.amir.caturday.ui.list.components.UrlImage
import com.amir.caturday.util.noRippleClickable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BreedDetails(
    data: BreedDetailsUiModel,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val maxOffsetPx = with(density) { screenWidthDp.toPx() }
    val alpha = 1f - (scrollState.value / maxOffsetPx).coerceIn(0f, 1f)
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            UrlImage(
                data.imageUrl,
                modifier =
                    Modifier
                        .alpha(alpha)
                        .aspectRatio(1F)
                        .fillMaxWidth(),
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .background(MaterialTheme.colorScheme.background),
            )
        }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
        ) {
            Box(
                modifier =
                    Modifier
                        .aspectRatio(1F)
                        .fillMaxWidth(),
            )
            Box(
                modifier =
                    Modifier
                        .offset {
                            IntOffset(x = 0, y = -24.dp.roundToPx())
                        }.fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Handle()
                    Text(
                        text = data.name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.size(10.dp))
                    data.attributes.forEachIndexed { index, item ->
                        AttributeRow(data = item, animationDelay = index * 100L)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        data.temperament.forEach {
                            Badge(it)
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    data.specs.forEach {
                        SpecRow(it)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = data.description,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    )

                    data.wikipediaUrl?.let {
                        WikipediaButton(it)
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = null,
                    tint = if (data.isFavorite) Red500 else Grey500,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .size(42.dp)
                            .noRippleClickable {
                                onFavoriteClick()
                            },
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun BreedDetailsPreview() {
    AppPreviewTheme {
        BreedDetails(
            data = breedDto.toBreed().toBreedDetailsUiModel(),
            onFavoriteClick = {},
        )
    }
}
