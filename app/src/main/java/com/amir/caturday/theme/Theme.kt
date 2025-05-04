package com.amir.caturday.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amir.caturday.domain.model.Theme

private val DarkBlueColorPalette = darkColorScheme(
    primary = Yellow500, //button, progress
    onPrimary = Black1000, //button text
    secondary = Yellow200, //progress indicator, heavier color
    onSecondary = Black1000, //button text, look good over secondary
    tertiary = DeepOrangeA700, //catch attention, accent
    onTertiary = White1000, //look good over accent
    error = Red700,
    onError = White1000,
    background = Grey900,
    onBackground = Grey300,
    outline = Grey400,
    outlineVariant = Grey600,
    surfaceBright = BlueGrey700,
    surface = BlueGrey800,
    surfaceDim = BlueGrey900,
)
private val LightGreenColorPalette = lightColorScheme(
    primary = Green500, //button, progress
    onPrimary = White1000, //button text
    secondary = Green700, //progress indicator, heavier color
    onSecondary = White1000, //button text, look good over secondary
    tertiary = GreenA400, //catch attention, accent
    onTertiary = Black1000, //look good over accent
    error = Red700,
    onError = White1000,
    background = Grey200,
    onBackground = Grey900,
    outline = Grey400,
    outlineVariant = Grey600,
    surfaceBright = Grey200,
    surface = White1000,
    surfaceDim = Grey400,
)
val LocalNavigation = compositionLocalOf<NavHostController> { throw Exception("Navigation not set") }
val LocalPreviewMode = compositionLocalOf<Boolean> { throw Exception("No LocalPreviewMode provided") }

@Composable
fun AppTheme(theme: Theme, content: @Composable () -> Unit) {
    val colors = when (theme) {
        Theme.DARK_BLUE -> DarkBlueColorPalette
        Theme.LIGHT_GREEN -> LightGreenColorPalette
        Theme.DEFAULT_THEME -> LightGreenColorPalette
    }
    val nav: NavHostController = rememberNavController()
    CompositionLocalProvider(
        LocalNavigation provides nav,
        LocalPreviewMode provides false,
    )
    {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            content = content
        )
    }
}

@Composable
fun AppPreviewTheme(
    content: @Composable () -> Unit
) {
    val nav: NavHostController = rememberNavController()
    CompositionLocalProvider(
        LocalNavigation provides nav,
        LocalPreviewMode provides true,
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            MaterialTheme(
                colorScheme = if (isSystemInDarkTheme()) DarkBlueColorPalette else LightGreenColorPalette,
                typography = typography,
                content = content,
            )
        }
    }
}