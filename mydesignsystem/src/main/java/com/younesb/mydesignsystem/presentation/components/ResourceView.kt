package com.younesb.mydesignsystem.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.younesb.mydesignsystem.domain.Error
import com.younesb.mydesignsystem.presentation.util.Resource
import soup.compose.material.motion.animation.materialSharedAxisX
import soup.compose.material.motion.animation.materialSharedAxisYIn
import soup.compose.material.motion.animation.materialSharedAxisYOut

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun<T, E: Error> ResourceView(
    resource: Resource<T, E>,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<Resource<T, E>>.() -> ContentTransform = {
        materialSharedAxisYIn(true, 100) togetherWith
        materialSharedAxisYOut(false, 100)
    },
    idleContent: @Composable () -> Unit = {},
    loadingContent: @Composable () -> Unit = {
        Box(Modifier.fillMaxSize()) {
            CircularWavyProgressIndicator(Modifier.align(Alignment.Center).size(64.dp))
        }
    },
    errorContent: @Composable (E) -> Unit = { _ ->
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "An error occurred",
                    textAlign = TextAlign.Center,
                )
            }
        }
    },
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        resource,
        modifier,
        transitionSpec
    ) {
        when (it) {
            is Resource.Error -> errorContent(it.error)
            Resource.Idle -> idleContent()
            Resource.Loading -> loadingContent()
            is Resource.Success -> content(it.data)
        }
    }
}