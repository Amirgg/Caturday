package com.amir.caturday.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme

@Composable
fun EmptyPage(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: Int? = null,
    showRetry: Boolean = false,
    onRetryClick: () -> Unit = {},
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(icon),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(100.dp),
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
            if (showRetry) {
                RetryButton(
                    onClick = onRetryClick,
                    modifier = Modifier.padding(top = 24.dp),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyPagePreview() {
    AppPreviewTheme {
        EmptyPage(
            modifier = Modifier.fillMaxSize(),
            title = "No Result",
            message = "Try a different word",
            icon = R.drawable.ic_search,
            showRetry = true,
        )
    }
}
