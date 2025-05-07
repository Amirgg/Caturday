package com.amir.caturday.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.theme.AppPreviewTheme

@Composable
fun Handle(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceDim),
        )
    }
}

@Composable
@PreviewLightDark
private fun HandlePreview() {
    AppPreviewTheme {
        Handle()
    }
}
