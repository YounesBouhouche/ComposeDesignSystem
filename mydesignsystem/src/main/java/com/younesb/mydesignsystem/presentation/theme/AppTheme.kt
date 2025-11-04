package com.younesb.mydesignsystem.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.kmpalette.color
import com.kmpalette.palette.graphics.Palette
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun AppTheme(
    primary: Color? = null,
    secondary: Color? = null,
    tertiary: Color? = null,
    dark: Boolean = isSystemInDarkTheme(),
    amoled: Boolean = false,
    dynamic: Boolean = true,
    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit,
) {
    val compatible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme =
        when {
            primary != null ->
                rememberDynamicColorScheme(
                    primary = primary,
                    secondary = secondary,
                    tertiary = tertiary,
                    isDark =  dark,
                    isAmoled = amoled
                )
            dynamic && compatible && dark -> {
                dynamicDarkColorScheme(LocalContext.current)
            }
            dynamic && compatible && !dark -> {
                dynamicLightColorScheme(LocalContext.current)
            }
            else -> MaterialTheme.colorScheme
        }
    DynamicMaterialTheme(
        primary = colorScheme.primary,
        secondary = colorScheme.secondary,
        tertiary = colorScheme.tertiary,
        animate = true,
        isDark = dark,
        isAmoled = amoled,
        typography = typography,
        content = content
    )
}


@Composable
fun AppTheme(
    palette: Palette? = null,
    dark: Boolean = isSystemInDarkTheme(),
    amoled: Boolean = false,
    dynamic: Boolean = true,
    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit,
) = AppTheme(
    palette?.vibrantSwatch?.color ?: palette?.dominantSwatch?.color,
    palette?.dominantSwatch?.color,
    palette?.mutedSwatch?.color,
    dark,
    amoled,
    dynamic,
    typography,
    content
)