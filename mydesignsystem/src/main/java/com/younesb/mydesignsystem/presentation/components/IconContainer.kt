package com.younesb.mydesignsystem.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A decorative container that displays an icon centered within a shaped background.
 * Useful for creating icon badges, feature cards, or decorative elements.
 *
 * Features:
 * - Customizable shape (defaults to Cookie12Sided from Material Expressive)
 * - Adjustable icon size relative to container
 * - Optional click handling
 * - Themeable colors
 *
 * @param icon The icon to display in the container.
 * @param modifier The modifier to be applied to the container.
 * @param iconRatio The size of the icon as a fraction of the container size (0.0 to 1.0).
 * @param iconTint The tint color to apply to the icon.
 * @param shape The shape of the container background.
 * @param background The background color of the container.
 * @param onClick Optional callback invoked when the container is clicked. If null, container is not clickable.
 *
 * @sample
 * ```
 * IconContainer(
 *     icon = Icons.Default.Star,
 *     modifier = Modifier.size(64.dp),
 *     iconRatio = 0.6f,
 *     shape = CircleShape,
 *     onClick = { /* Handle click */ }
 * )
 * ```
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IconContainer(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconRatio: Float = 0.5f,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: Shape = MaterialShapes.Cookie12Sided.toShape(),
    background: Color = MaterialTheme.colorScheme.surfaceContainer,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier
            .clip(shape)
            .background(background)
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            null,
            tint = iconTint,
            modifier = Modifier.fillMaxSize(iconRatio)
        )
    }
}