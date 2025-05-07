package com.amir.caturday.ui.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme

@Composable
fun WikipediaButton(
    data: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    Box(modifier = modifier) {
        Button(onClick = {
            uriHandler.openUri(data)
        }, modifier = Modifier.padding(top = 24.dp)) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                painter = painterResource(id = R.drawable.ic_external_link),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(24.dp)
                        .padding(end = 6.dp),
            )
            Text(
                text = stringResource(R.string.show_in_wikipedia),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun WikipediaButtonPreview() {
    AppPreviewTheme {
        WikipediaButton("")
    }
}
