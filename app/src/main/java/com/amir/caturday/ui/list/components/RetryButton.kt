package com.amir.caturday.ui.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme

@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Button(
            shape = CircleShape,
            onClick = onClick,
            modifier =
            Modifier,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(24.dp)
                        .padding(end = 6.dp),
            )
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun RetryButtonPreview() {
    AppPreviewTheme {
        RetryButton({})
    }
}
