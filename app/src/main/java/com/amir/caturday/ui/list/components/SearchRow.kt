package com.amir.caturday.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme

@Composable
fun SearchRow(
    onSearchQueryChanged: (query: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchState by remember { mutableStateOf(TextFieldValue("")) }
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier =
                Modifier
                    .clip(RoundedCornerShape(50))
                    .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(50))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp),
            )
            BasicTextField(
                value = searchState,
                onValueChange = {
                    searchState = it
                    onSearchQueryChanged(it.text)
                },
                textStyle =
                    MaterialTheme.typography.bodyMedium.copy(
                        MaterialTheme.colorScheme.primary,
                    ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier =
                    Modifier
                        .padding(horizontal = 6.dp)
                        .weight(1F),
            )
            if (searchState.text.isNotEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier =
                        Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .clip(CircleShape)
                            .clickable {
                                searchState = TextFieldValue()
                                onSearchQueryChanged("")
                            }.size(32.dp)
                            .padding(8.dp),
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun SearchRowPreview() {
    AppPreviewTheme {
        Column(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
        ) {
            SearchRow(
                {},
            )
        }
    }
}
