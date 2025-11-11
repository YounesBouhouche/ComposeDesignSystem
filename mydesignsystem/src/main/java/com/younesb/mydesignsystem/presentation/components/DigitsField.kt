package com.younesb.mydesignsystem.presentation.components

import android.view.KeyEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.younesb.mydesignsystem.presentation.util.DigitsFieldState
import com.younesb.mydesignsystem.presentation.util.rememberDigitsFieldState

/**
 * A composable that displays a field for entering a multi-digit code (e.g., OTP, PIN).
 * Each digit is displayed in its own box with automatic focus management.
 *
 * The component automatically moves focus to the next box when a digit is entered,
 * and moves back when backspace is pressed on an empty box. When all digits are entered,
 * the keyboard is automatically hidden.
 *
 * @param modifier The modifier to be applied to the component.
 * @param state The state holder for the digits field. Use [rememberDigitsFieldState] to create it.
 * @param horizontalArrangement The horizontal arrangement of the digit boxes.
 * @param contentPadding The padding to be applied to the content inside each digit box.
 *
 * @sample
 * ```
 * val state = rememberDigitsFieldState(4)
 * DigitsField(
 *     state = state,
 *     modifier = Modifier.fillMaxWidth()
 * )
 * // Access the code with: state.code
 * // Check if complete with: state.isCodeComplete()
 * // Set error state with: state.isError = true
 * ```
 */
@Suppress("Unused")
@Composable
fun DigitsField(
    modifier: Modifier = Modifier,
    state: DigitsFieldState = rememberDigitsFieldState(4),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val focusRequesters = remember {
        List(state.digits.size) {
            FocusRequester()
        }
    }
    val keyboardManager = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }
    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.isCodeComplete()
        if(allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        for (i in state.digits.indices) {
            DigitBox(
                digit = state.digits[i],
                focusRequester = focusRequesters[i],
                isError = state.isError,
                onNumberChanged = { value ->
                    state.enterNumber(value, i)
                },
                onKeyboardBack = {
                    state.backspace()
                },
                onFocusChanged = { isFocused ->
                    if (isFocused) {
                        state.changeFocusedIndex(i)
                    }
                },
                modifier = Modifier.weight(1f),
                contentPadding = contentPadding,
            )
        }
    }
}


@Composable
internal fun DigitBox(
    digit: Int?,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val text by remember(digit) {
        mutableStateOf(
            TextFieldValue(
                text = digit?.toString().orEmpty(),
                selection = TextRange(
                    index = if(digit != null) 1 else 0
                )
            )
        )
    }
    var isFocused by remember {
        mutableStateOf(false)
    }
    val borderStroke by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val borderColor by animateColorAsState(
        if (isError) MaterialTheme.colorScheme.error
        else if (isFocused or (digit != null)) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline
    )
    val color by animateColorAsState(
        if (isError) MaterialTheme.colorScheme.error
        else if (isFocused) MaterialTheme.colorScheme.onSurface
        else if (digit != null) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant
    )
    Box(
        modifier = modifier
            .border(
                width = borderStroke,
                color = borderColor,
                shape = shape
            )
            .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                val newNumber = newText.text
                if(newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                }
            },
            enabled = enabled,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            singleLine = true,
            textStyle = style.copy(textAlign = TextAlign.Center, color = color),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .padding(10.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged(it.isFocused)
                }
                .onKeyEvent { event ->
                    val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if (didPressDelete && digit == null) {
                        onKeyboardBack()
                    }
                    false
                }
                .semantics {
                    contentType = ContentType.SmsOtpCode
                },
            decorationBox = { innerBox ->
                Box(Modifier.wrapContentSize().padding(contentPadding)) {
                    innerBox()
                    if(!isFocused && digit == null) {
                        Text(
                            text = "-",
                            style = style,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize()
                        )
                    }
                }
            }
        )
    }
}
