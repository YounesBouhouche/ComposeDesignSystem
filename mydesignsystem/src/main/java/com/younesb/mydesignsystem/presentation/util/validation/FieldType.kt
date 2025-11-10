package com.younesb.mydesignsystem.presentation.util.validation

sealed class FieldType(
    val regex: String,
    open val minLength: Int = 0,
    open val maxLength: Int = Int.MAX_VALUE,
) {
    // Simple field types
    data class FirstName(
        override val minLength: Int = 2,
        override val maxLength: Int = 30
    ) : FieldType(
        regex = "^[A-Za-z]{${minLength},${maxLength}}$",
    )
    data class LastName(
        override val minLength: Int = 2,
        override val maxLength: Int = 30
    ) : FieldType(
        regex = "^[A-Za-z]{${minLength},${maxLength}}$",
    )
    data object Email : FieldType(
        regex = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)",
        minLength = 5,
        maxLength = 254,
    )
    data class Password(
        override val minLength: Int = 8,
        override val maxLength: Int = 20,
        val requireSpecialChar: Boolean = true,
        val requireNumber: Boolean = true,
    ) : FieldType(
        regex = buildString {
            append("^")
            append("(?=.*[a-zA-Z])") // At least one letter
            if (requireNumber) append("(?=.*\\d)") // At least one digit
            if (requireSpecialChar) append("(?=.*[@$!%*?&])") // At least one special character
            append(".{${minLength},${maxLength}}") // Length constraint
            append("$")
        },
    )
    data class PhoneNumber(val countryCode: String = "") : FieldType(
        minLength = 7,
        maxLength = 15 + countryCode.length,
        regex = if (countryCode.isNotEmpty()) {
            "^\\+${countryCode}[0-9]{7,15}$"
        } else {
            "^[0-9]{7,15}$"
        },
    )
    data class Username(
        override val minLength: Int = 3,
        override val maxLength: Int = 15
    ) : FieldType(
        minLength = minLength,
        maxLength = maxLength,
        regex = "^[a-zA-Z0-9._-]{3,}$",
    )

    // Payment field types
    data object CreditCardNumber : FieldType(
        regex = "^[0-9]{13,19}$",
        minLength = 13,
        maxLength = 19,
    )
    data object CVV : FieldType(
        regex = "^[0-9]{3,4}$",
        minLength = 3,
        maxLength = 4,
    )
    data object ExpirationDate : FieldType(
        regex = "^(0[1-9]|1[0-2])\\/([0-9]{2})$",
        minLength = 5,
        maxLength = 5,
    )

    // Custom field type
    data class Custom(
        override val minLength: Int = 0,
        override val maxLength: Int = Int.MAX_VALUE,
        val customRegex: String
    ) : FieldType(
        regex = customRegex,
    )

    fun validate(input: String, stringToMatch: String? = null): InputError? =
        when {
            input.isBlank() -> InputError.EMPTY
            minLength > input.length -> InputError.TOO_SHORT
            maxLength < input.length -> InputError.TOO_LONG
            !Regex(regex).matches(input) -> InputError.INVALID_FORMAT
            input != (stringToMatch ?: input) -> InputError.MISMATCH
            else -> null
        }
}