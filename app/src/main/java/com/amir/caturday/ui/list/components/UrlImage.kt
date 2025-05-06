package com.amir.caturday.ui.list.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil3.compose.AsyncImage
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.theme.LocalPreviewMode

@Composable
fun UrlImage(
    data: String?,
    modifier: Modifier = Modifier,
) {
    val isInPreviewMode = LocalPreviewMode.current
    if (isInPreviewMode) {
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = null,
            modifier = modifier,
        )
    } else {
        data?.let {
            AsyncImage(
                model = it,
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = modifier,
            )
        } ?: run {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = null,
                modifier = modifier,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun UrlImagePreview() {
    AppPreviewTheme {
        UrlImage(null)
    }
}
