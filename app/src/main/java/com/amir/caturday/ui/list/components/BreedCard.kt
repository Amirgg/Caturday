package com.amir.caturday.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.theme.Grey500
import com.amir.caturday.theme.Red500
import com.amir.caturday.ui.list.model.BreedCardUiModel
import com.amir.caturday.ui.list.model.toBreedCardUiModel
import com.amir.caturday.util.noRippleClickable

@Composable
fun BreedCard(
    data: BreedCardUiModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onClick() }
                    .background(MaterialTheme.colorScheme.surface)
                    .height(IntrinsicSize.Max),
        ) {
            UrlImage(
                data = data.imageUrl,
                modifier = Modifier.size(120.dp),
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                        .padding(vertical = 10.dp)
                        .fillMaxHeight(),
            ) {
                Text(
                    text = data.name,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                )

                Text(
                    text = stringResource(R.string.origin_x, data.origin),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier =
                        Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                ) {
                    data.temperaments.forEach {
                        Badge(it)
                    }
                }
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
                    .size(32.dp)
                    .noRippleClickable {
                        onFavoriteClick()
                    },
        )
    }
}

@Composable
@PreviewLightDark
private fun BreedCardPreview() {
    AppPreviewTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            BreedCard(
                data = breedDto.toBreed().toBreedCardUiModel(),
                onClick = {},
                onFavoriteClick = {},
                modifier = Modifier.fillMaxWidth().padding(10.dp),
            )
        }
    }
}
