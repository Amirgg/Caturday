package com.amir.caturday.ui.details.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.ui.details.model.SpecRowUiModel
import com.amir.caturday.ui.details.model.toBreedDetailsUiModel

@Composable
fun SpecRow(
    data: SpecRowUiModel,
    modifier: Modifier = Modifier,
) {
    val pathEffect by remember {
        mutableStateOf(PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))
    }
    val lineColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5F)
    Box(modifier = modifier) {
        Canvas(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.Center),
        ) {
            drawLine(
                color = lineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(data.title),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(end = 6.dp),
            )
            Text(
                text = data.value,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 6.dp),
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun SpecRowPreview() {
    AppPreviewTheme {
        SpecRow(
            breedDto
                .toBreed()
                .toBreedDetailsUiModel()
                .specs
                .random(),
        )
    }
}
