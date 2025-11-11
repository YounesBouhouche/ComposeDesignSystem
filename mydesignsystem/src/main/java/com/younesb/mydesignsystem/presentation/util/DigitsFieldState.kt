package com.younesb.mydesignsystem.presentation.util

import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.Int
import kotlin.collections.map


class DigitsFieldState(
    val initialDigits: List<Int?> = (1..4).map { null },
    val initialFocusedIndex: Int? = null,
    initialIsError: Boolean = false,
) {
    var digits by mutableStateOf(initialDigits)
        private set

    var focusedIndex by mutableStateOf(initialFocusedIndex)
        private set

    var isError by mutableStateOf(initialIsError)

    fun isCodeComplete(): Boolean {
        return digits.all { it != null }
    }

    val code by derivedStateOf {
        digits.joinToString(separator = "") { it?.toString() ?: "" }
    }

    private fun getPreviousFocusedIndex(): Int? {
        return focusedIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        currentFocusedIndex: Int
    ): Int {
        digits.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    private fun getNextFocusedTextFieldIndex(): Int? {
        if(focusedIndex == null) {
            return null
        }

        if(focusedIndex == digits.size - 1) {
            return focusedIndex
        }

        val index = getFirstEmptyFieldIndexAfterFocusedIndex(currentFocusedIndex = focusedIndex!!)
        return index
    }

    fun changeFocusedIndex(index: Int) {
        focusedIndex = index
    }

    fun backspace() {
        val previousIndex = getPreviousFocusedIndex()
        digits = digits.mapIndexed { index, number ->
            if (index == previousIndex) {
                null
            } else {
                number
            }
        }
        focusedIndex = previousIndex
    }

    fun enterNumber(number: Int?, index: Int) {
        val newCode = digits.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        digits = newCode
        focusedIndex =
            if (wasNumberRemoved || digits.getOrNull(index) == null) focusedIndex
            else getNextFocusedTextFieldIndex()
    }

    object Saver: androidx.compose.runtime.saveable.Saver<DigitsFieldState, Any> {
        override fun SaverScope.save(value: DigitsFieldState): Any {
            return mapOf(
                "digits" to value.digits,
                "focusedIndex" to value.focusedIndex,
            )
        }

        override fun restore(value: Any): DigitsFieldState {
            val map = value as Map<*, *>
            val digits = (map["digits"] as List<Any?>).map { it as Int? }
            val focusedIndex = map["focusedIndex"] as Int?
            return DigitsFieldState(
                initialDigits = digits,
                initialFocusedIndex = focusedIndex,
            )
        }
    }
}

@Composable
fun rememberDigitsFieldState(
    initialDigits: List<Int?> = (1..4).map { null },
    initialFocusedIndex: Int? = null,
): DigitsFieldState {
    return rememberSaveable(saver = DigitsFieldState.Saver) {
        DigitsFieldState(
            initialDigits = initialDigits,
            initialFocusedIndex = initialFocusedIndex,
        )
    }
}


@Composable
fun rememberDigitsFieldState(
    @IntRange(from = 1)
    initialDigitsCount: Int = 4,
    initialFocusedIndex: Int? = null,
) = rememberDigitsFieldState(
    (1..initialDigitsCount).map { null },
    initialFocusedIndex,
)