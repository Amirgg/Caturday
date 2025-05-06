package com.amir.caturday.ui.details.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.ui.details.model.BreedAttributeUiModel
import com.amir.caturday.ui.details.model.toBreedDetailsUiModel
import kotlinx.coroutines.delay

@Composable
fun AttributeRow(
    data: BreedAttributeUiModel,
    animationDelay: Long = 0,
    modifier: Modifier = Modifier,
) {
    var counter by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        delay(animationDelay)
        repeat(5) {
            delay(50)
            counter++
        }
    }
    Box(modifier = modifier) {
        Row {
            Text(
                text = stringResource(data.title),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1F),
            )
            repeat(5) {
                val animateAlpha by animateFloatAsState(
                    targetValue = if (it < counter) 1F else 0F,
                    animationSpec = tween(durationMillis = 500, delayMillis = 0),
                )
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    tint =
                        if (data.level > it) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.surfaceDim
                        },
                    modifier =
                        Modifier
                            .size(24.dp)
                            .alpha(animateAlpha),
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun AttributeRowPreview() {
    AppPreviewTheme {
        AttributeRow(
            breedDto
                .toBreed()
                .toBreedDetailsUiModel()
                .attributes
                .random(),
        )
    }
}
