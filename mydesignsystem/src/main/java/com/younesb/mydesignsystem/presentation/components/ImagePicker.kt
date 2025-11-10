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