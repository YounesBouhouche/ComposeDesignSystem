package com.younesb.mydesignsystem.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

/**
 * A Material 3 outlined text field with built-in clear button and enhanced features.
 * Wraps OutlinedTextField with commonly needed functionality.
 *
 * Features:
 * - Animated clear button (appears when text is not empty)
 * - Autofill support for improved UX
 * - Error state with supporting text
 * - Customizable leading/trailing icons
 * - Full keyboard options support
 *
 * @param value The current text value of the field.
 * @param onValueChange Callback invoked when the text changes.
 * @param modifier The modifier to be applied to the text field.
 * @param enabled Whether the text field is enabled for input.
 * @param label The label text to display.
 * @param placeholder The placeholder text to display when empty.
 * @param leadingIcon Optional icon to display at the start of the field.
 * @param trailingIcon Optional composable for trailing content (defaults to animated clear button).
 * @param autoCompleteContentType Content type hint for autofill services.
 * @param error Optional error message to display below the field.
 * @param visualTransformation Transformation to apply to the displayed text (e.g., password masking).
 * @param imeAction The IME action to show on the keyboard.
 * @param keyboardType The type of keyboard to display.
 * @param keyboardActions Actions to perform on keyboard events.
 * @param singleLine Whether the field should be single line.
 * @param interactionSource Optional custom interaction source.
 *
 * @sample
 * ```
 * var email by remember { mutableStateOf("") }
 * TextField(
 *     value = email,
 *     onValueChange = { email = it },
 *     label = "Email",
 *     placeholder = "Enter your email",
 *     leadingIcon = Icons.Default.Email,
 *     keyboardType = KeyboardType.Email,
 *     autoCompleteContentType = ContentType.EmailAddress
 * )
 * ```
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: (@Composable () -> Unit)? = {
        AnimatedVisibility(
            value.isNotEmpty(),
            enter = materialSharedAxisZIn(true),
            exit = materialSharedAxisZOut(false)
        ) {
            ExpressiveIconButton(
                Icons.Filled.Clear,
                size = IconButtonDefaults.mediumIconSize
            ) {
                onValueChange("")
            }
        }
    },
    autoCompleteContentType: ContentType? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth().semantics {
            autoCompleteContentType?.let {
                contentType = it
            }
        },
        enabled = enabled,
        label = { Text(label) },
        interactionSource = interactionSource,
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon?.let { { Icon(it, contentDescription = null) } },
        trailingIcon = trailingIcon,
        supportingText = error?.let { { Text(it) } },
        isError = error != null,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = shape,
        colors = colors
    )
}

/**
 * A specialized text field for password input with visibility toggle.
 * Built on top of [TextField] with password-specific defaults.
 *
 * Features:
 * - Password visibility toggle button (eye icon)
 * - Password masking by default
 * - Autofill support for passwords
 * - Password keyboard type
 * - All features from [TextField]
 *
 * @param value The current password text value.
 * @param onValueChange Callback invoked when the password text changes.
 * @param passwordHidden Whether the password should be masked (true) or visible (false).
 * @param onPasswordHiddenChange Callback invoked when the visibility toggle is clicked.
 * @param modifier The modifier to be applied to the text field.
 * @param enabled Whether the text field is enabled for input.
 * @param label The label text to display.
 * @param placeholder The placeholder text to display when empty.
 * @param leadingIcon Optional icon to display at the start of the field.
 * @param trailingIcon Optional composable for trailing content (defaults to visibility toggle).
 * @param autoCompleteContentType Content type hint for autofill (defaults to Password).
 * @param error Optional error message to display below the field.
 * @param imeAction The IME action to show on the keyboard.
 * @param keyboardType The type of keyboard to display (defaults to Password).
 * @param keyboardActions Actions to perform on keyboard events.
 * @param singleLine Whether the field should be single line.
 *
 * @sample
 * ```
 * var password by remember { mutableStateOf("") }
 * var passwordHidden by remember { mutableStateOf(true) }
 *
 * PasswordTextField(
 *     value = password,
 *     onValueChange = { password = it },
 *     passwordHidden = passwordHidden,
 *     onPasswordHiddenChange = { passwordHidden = it },
 *     label = "Password",
 *     leadingIcon = Icons.Default.Lock
 * )
 * ```
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordHidden: Boolean,
    onPasswordHiddenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: (@Composable () -> Unit)? = {
        ExpressiveIconButton(
            if (passwordHidden) Icons.Default.Visibility
            else Icons.Default.VisibilityOff,
        ) {
            onPasswordHiddenChange(!passwordHidden)
        }
    },
    autoCompleteContentType: ContentType? = ContentType.Password,
    error: String? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        error = error,
        autoCompleteContentType = autoCompleteContentType,
        visualTransformation =
            if (passwordHidden) PasswordVisualTransformation()
            else VisualTransformation.None,
        imeAction = imeAction,
        keyboardType = keyboardType,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = shape,
        colors = colors
    )
}