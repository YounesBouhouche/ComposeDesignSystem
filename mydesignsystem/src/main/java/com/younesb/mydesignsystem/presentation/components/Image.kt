package com.younesb.mydesignsystem.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import kotlin.let

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Image(
    model: Any?,
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    alt: (@Composable () -> Unit)? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: Shape = MaterialTheme.shapes.medium,
    background: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    fraction: Float = .5f,
    onClick: (() -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = "",
        modifier = modifier
            .clip(shape)
            .background(background)
            .then(
                if (onClick != null) {
                    Modifier.clickable(role = Role.Image) { onClick() }
                } else {
                    Modifier
                }
            ),
        contentScale = ContentScale.Crop,
        loading = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularWavyProgressIndicator(Modifier.fillMaxSize(.5f))
            }
        },
        error = {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(fraction),
                        tint = iconTint
                    )
                }
                ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                    alt?.invoke()
                }
            }
        },
        onError = onError,
        onSuccess = onSuccess,
    )
}