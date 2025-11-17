package com.younesb.mydesignsystem.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ToggleButtonColors
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ToggleButtonsRow(
    count: Int,
    checked: @Composable (Int) -> Boolean,
    icon: @Composable (Int) -> ImageVector?,
    text: @Composable (Int) -> String,
    modifier: Modifier = Modifier,
    outlined: (Int) -> Boolean = { false },
    enabled: @Composable (Int) -> Boolean = { true },
    colors: @Composable (Int) -> ToggleButtonColors = {
        if (outlined(it))
            ToggleButtonDefaults.outlinedToggleButtonColors()
        else
            ToggleButtonDefaults.toggleButtonColors()
                                                },
    size: Dp = ButtonDefaults.MediumContainerHeight,
    expandedWeight: Float = 1.15f,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    onCheckedChange: (Int, Boolean) -> Unit = { _, _ -> },
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(count) { index ->
            val interactionSource = remember {
                MutableInteractionSource()
            }
            val pressed by interactionSource.collectIsPressedAsState()
            val weight by animateFloatAsState(
                if (pressed) expandedWeight else 1f
            )
            ExpressiveToggleButton(
                checked = checked(index),
                text = text(index),
                icon = icon(index),
                size = size,
                outlined = outlined(index),
                enabled = enabled(index),
                colors = colors(index),
                onCheckedChange = { onCheckedChange(index, it) },
                interactionSource = interactionSource,
                modifier = Modifier.weight(weight)
            )
        }
    }
}