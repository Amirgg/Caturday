package com.amir.caturday.ui.settings.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.amir.caturday.R
import com.amir.caturday.theme.AppPreviewTheme
import com.amir.caturday.theme.Black1000
import com.amir.caturday.theme.Grey200
import com.amir.caturday.theme.Grey400
import com.amir.caturday.theme.Grey700
import com.amir.caturday.theme.Grey900
import com.amir.caturday.theme.Yellow500

@Composable
fun CeilingLight(
    data: Boolean,
    onThemeChange: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val transition =
        updateTransition(
            targetState = if (data) 1 else 0,
            label = "",
        )
    val animateBackgroundColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 1000) },
    ) {
        if (it == 1) Grey200 else Grey900
    }
    val animateCeilingLightColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 1000) },
    ) {
        if (it == 1) Grey400 else Grey700
    }
    val animateBulbColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 1000, delayMillis = 1000) },
    ) {
        if (it == 1) Grey700 else Yellow500
    }
    val animateTextColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = 1000) },
    ) {
        if (it == 1) Black1000 else Grey200
    }
    val animateLightAlpha by transition.animateFloat(
        label = "",
        transitionSpec = { tween(durationMillis = 1000, delayMillis = 1000) },
    ) {
        if (it == 1) 0F else 0.3F
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(animateBackgroundColor),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val widthHalf = size.width / 2
                        val lightSize = 160.dp.toPx()
                        val bulbSize = 40.dp.toPx()
                        val wireLength = 200.dp.toPx()
                        drawArc(
                            brush =
                                Brush.radialGradient(
                                    listOf(
                                        Yellow500,
                                        Color.Transparent,
                                    ),
                                    center = Offset(widthHalf, wireLength + lightSize / 2),
                                    radius = lightSize,
                                ),
                            startAngle = 0f,
                            sweepAngle = 360F,
                            useCenter = true,
                            topLeft = Offset(x = widthHalf - (lightSize), y = wireLength + lightSize / 2 - lightSize),
                            size = Size(lightSize * 2, lightSize * 2),
                            alpha = animateLightAlpha,
                        )
                        drawLine(
                            color = animateCeilingLightColor,
                            strokeWidth = 4.dp.toPx(),
                            start = Offset(x = widthHalf, y = 0F),
                            end = Offset(x = widthHalf, y = wireLength),
                        )
                        drawArc(
                            color = animateCeilingLightColor,
                            startAngle = 180f,
                            sweepAngle = 180f,
                            useCenter = true,
                            topLeft = Offset(x = widthHalf - (lightSize / 2), y = wireLength),
                            size = Size(lightSize, lightSize),
                        )
                        drawArc(
                            color = animateBulbColor,
                            startAngle = 0f,
                            sweepAngle = 180f,
                            useCenter = true,
                            topLeft = Offset(x = widthHalf - (bulbSize / 2), y = wireLength + lightSize / 2 - bulbSize / 2),
                            size = Size(bulbSize, bulbSize),
                        )
                        drawLine(
                            color = animateCeilingLightColor,
                            strokeWidth = 2.dp.toPx(),
                            start = Offset(x = widthHalf + (lightSize / 2) - 10.dp.toPx(), y = wireLength + lightSize / 2),
                            end = Offset(x = widthHalf + (lightSize / 2) - 10.dp.toPx(), y = wireLength * 2),
                        )
                        drawLine(
                            color = animateCeilingLightColor,
                            strokeWidth = 2.dp.toPx(),
                            start = Offset(x = widthHalf + (lightSize / 2) - 10.dp.toPx(), y = wireLength + lightSize / 2),
                            end = Offset(x = widthHalf + (lightSize / 2) - 10.dp.toPx(), y = wireLength * 2),
                        )
                    }.padding(10.dp),
        ) {
            Switch(
                checked = data,
                onCheckedChange = {
                    onThemeChange()
                },
                modifier =
                    Modifier
                        .offset {
                            IntOffset(
                                x = screenWidthDp.roundToPx() / 2 + 34.dp.roundToPx(),
                                y = 350.dp.roundToPx(),
                            )
                        }.rotate(90F),
            )
            Text(
                text = stringResource(if (data) R.string.light_theme else R.string.dark_theme),
                style = MaterialTheme.typography.headlineMedium,
                color = animateTextColor,
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(24.dp),
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun CeilingLightPreview() {
    AppPreviewTheme {
        CeilingLight(data = false, onThemeChange = {})
    }
}
