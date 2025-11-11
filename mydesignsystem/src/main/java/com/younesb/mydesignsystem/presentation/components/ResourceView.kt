package com.younesb.mydesignsystem.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
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
import soup.compose.material.motion.animation.materialSharedAxisYIn
import soup.compose.material.motion.animation.materialSharedAxisYOut

/**
 * A component that handles different states of a resource (Idle, Loading, Success, Error)
 * with smooth animated transitions between states.
 *
 * This component simplifies handling async operations by providing default UI for each state
 * while allowing full customization. Uses Material Motion's shared axis transitions.
 *
 * The Resource type should be a sealed class with states: Idle, Loading, Success(T), and Error(E).
 *
 * Features:
 * - Animated state transitions
 * - Default loading indicator (CircularWavyProgressIndicator)
 * - Default error UI with icon and message
 * - Fully customizable content for each state
 * - Type-safe error handling with custom error types
 *
 * @param T The type of successful data.
 * @param E The type of error (must extend Error).
 * @param resource The current resource state to display.
 * @param modifier The modifier to be applied to the container.
 * @param transitionSpec The transition animation between states.
 * @param idleContent Composable to display in Idle state (default is empty).
 * @param loadingContent Composable to display in Loading state (default is centered progress indicator).
 * @param errorContent Composable to display in Error state, receives the error object.
 * @param content Composable to display in Success state, receives the data object.
 *
 * @sample
 * ```
 * val userResource: Resource<User, ApiError> by viewModel.user.collectAsState()
 *
 * ResourceView(
 *     resource = userResource,
 *     errorContent = { error ->
 *         Text("Error: ${error.message}")
 *     },
 *     content = { user ->
 *         UserProfile(user)
 *     }
 * )
 * ```
 */
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