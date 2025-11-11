package com.younesb.mydesignsystem.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

/**
 * An expressive button component with support for icons, loading states, and multiple sizes.
 * This button uses Material 3's expressive design system and can be displayed as filled or outlined.
 *
 * Features:
 * - Animated loading indicator that replaces content
 * - Optional leading icon
 * - Customizable size with automatic padding and typography adjustments
 * - Filled or outlined variants
 *
 * @param text The composable content for the button text. Can be null for icon-only buttons.
 * @param size The height of the button in Dp. Determines padding and icon size automatically.
 * @param modifier The modifier to be applied to the button.
 * @param icon Optional leading icon to display before the text.
 * @param loading Whether to show a loading indicator instead of content.
 * @param enabled Whether the button is enabled for interaction.
 * @param outlined Whether to use an outlined style instead of filled.
 * @param colors The colors to use for the button. Defaults based on outlined parameter.
 * @param interactionSource Optional custom interaction source for the button.
 * @param onClick Callback invoked when the button is clicked.
 *
 * @sample
 * ```
 * ExpressiveButton(
 *     text = { Text("Submit") },
 *     size = 56.dp,
 *     icon = Icons.Default.Send,
 *     loading = isLoading,
 *     onClick = { /* Handle click */ }
 * )
 * ```
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpressiveButton(
    text: (@Composable () -> Unit)?,
    size: Dp,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    outlined: Boolean = false,
    colors: ButtonColors =
        if (outlined)
            ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        else
            ButtonDefaults.buttonColors(),
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
) {
    if (outlined)
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.heightIn(size),
            contentPadding = ButtonDefaults.contentPaddingFor(size),
            enabled = enabled,
            colors = colors,
            shapes = ButtonDefaults.shapesFor(size),
            interactionSource = interactionSource
        ) {
            AnimatedContent(loading) { isLoading ->
                if (isLoading)
                    Row {
                        LoadingIndicator(
                            Modifier.size(ButtonDefaults.iconSizeFor(size)),
                            color = ButtonDefaults.buttonColors().contentColor
                        )
                    }
                else {
                    Row {
                        icon?.let {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
                            )
                        }
                        if ((icon != null) and (text != null))
                            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
                        text?.let {
                            ProvideTextStyle(
                                ButtonDefaults.textStyleFor(size),
                                it
                            )
                        }
                    }
                }
            }
        }
    else
        Button(
            onClick = onClick,
            modifier = modifier.heightIn(size),
            contentPadding = ButtonDefaults.contentPaddingFor(size),
            enabled = enabled,
            colors = colors,
            shapes = ButtonDefaults.shapesFor(size),
            interactionSource = interactionSource
        ) {
            AnimatedContent(loading) { isLoading ->
                if (isLoading)
                    Row {
                        LoadingIndicator(
                            Modifier.size(ButtonDefaults.iconSizeFor(size)),
                            color = ButtonDefaults.buttonColors().contentColor
                        )
                    }
                else {
                    Row {
                        icon?.let {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
                            )
                            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
                        }
                        text?.let {
                            ProvideTextStyle(
                                ButtonDefaults.textStyleFor(size),
                                it
                            )
                        }
                    }
                }
            }
        }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpressiveButton(
    text: String,
    size: Dp,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    outlined: Boolean = false,
    colors: ButtonColors =
        if (outlined)
            ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        else
            ButtonDefaults.buttonColors(),
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
) = ExpressiveButton(
    { Text(text, maxLines = 1, overflow = TextOverflow.Ellipsis) },
    size,
    modifier,
    icon,
    loading,
    enabled,
    outlined,
    colors,
    interactionSource,
    onClick
)
