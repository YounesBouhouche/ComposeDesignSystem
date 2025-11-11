package com.younesb.mydesignsystem.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * An image picker component that allows users to select images from their device.
 * Built on top of the [Image] component with integrated photo picker functionality.
 *
 * When clicked, launches the Android photo picker to select an image.
 * Displays the selected image or a fallback icon with optional text.
 *
 * Features:
 * - Android photo picker integration
 * - Image preview after selection
 * - Fallback icon when no image selected
 * - All styling options from [Image] component
 *
 * @param image The currently selected image URI, or null if none selected.
 * @param onImageChange Callback invoked when a new image is selected. Receives the image URI or null.
 * @param modifier The modifier to be applied to the image picker.
 * @param icon The icon to display when no image is selected (defaults to AddAPhoto).
 * @param alt Optional composable content to display below the icon when no image selected.
 * @param iconTint The tint color for the fallback icon.
 * @param shape The shape to clip the image container.
 * @param background The background color of the container.
 * @param fraction The size of the fallback icon as a fraction of the container.
 *
 * @sample
 * ```
 * var selectedImage by remember { mutableStateOf<Uri?>(null) }
 * ImagePicker(
 *     image = selectedImage,
 *     onImageChange = { selectedImage = it },
 *     modifier = Modifier.size(150.dp),
 *     alt = { Text("Tap to add photo") }
 * )
 * ```
 */
@Composable
fun ImagePicker(
    image: Uri?,
    onImageChange: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.AddAPhoto,
    alt: (@Composable () -> Unit)? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: Shape = MaterialTheme.shapes.medium,
    background: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    fraction: Float = .5f,
) {
    val picker = rememberLauncherForActivityResult(PickVisualMedia()) {
        onImageChange(it)
    }
    Image(
        model = image,
        icon = icon,
        modifier = modifier,
        alt = alt,
        iconTint = iconTint,
        shape = shape,
        background = background,
        fraction = fraction,
        onClick = {
            picker.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        },
    )
}