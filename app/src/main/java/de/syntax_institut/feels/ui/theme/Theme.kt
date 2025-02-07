package de.syntax_institut.feels.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import kotlinx.serialization.json.JsonNull.content

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = SandBeige,

    background = Color(0xFF2C2825),
    surface = Color(0xFFD513A4),
    onPrimary = Color(0xFFD513A4),
    onSecondary = Color(0xFFD513A4),
    onTertiary = Color(0xFFD51313),
    onBackground = Color(0xFFFCFCFD),
    onSurface = Color(0xFFFFFFFF),
    surfaceContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = SandBeige,


    background = Color.Transparent,
    surface = Color(0xFFD513A4),
    onPrimary = Color.Red,
    onSecondary = Color.Red,
    onTertiary = Color.Red,
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF1C1B1F),
    surfaceContainer = Color.Gray

)

@Composable
fun FeelsTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkMode -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}