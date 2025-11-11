package com.younesb.mydesignsystem.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.younesb.mydesignsystem.R

/**
 * A standardized button row for dialog actions with OK, Cancel, and optional neutral buttons.
 * Follows Material Design guidelines for dialog button layout and styling.
 *
 * The component automatically handles button spacing and layout:
 * - Neutral button on the left (if provided)
 * - Cancel button (outlined) in the middle (if provided)
 * - OK button (filled) on the right (if provided)
 *
 * At least one button should be provided for the component to be useful.
 *
 * @param cancelListener Callback invoked when cancel button is clicked. If null, cancel button is hidden.
 * @param cancelText The text to display on the cancel button (defaults to localized "Cancel").
 * @param okListener Callback invoked when OK button is clicked. If null, OK button is hidden.
 * @param neutral Optional composable content for a neutral action button (e.g., "Learn More").
 *
 * @sample
 * ```
 * DialogButtons(
 *     cancelListener = { dialog.dismiss() },
 *     okListener = {
 *         saveChanges()
 *         dialog.dismiss()
 *     }
 * )
 *
 * // With neutral button
 * DialogButtons(
 *     cancelListener = { dialog.dismiss() },
 *     okListener = { confirmAction() },
 *     neutral = {
 *         TextButton(onClick = { showHelp() }) {
 *             Text("Help")
 *         }
 *     }
 * )
 * ```
 */
@Composable
fun DialogButtons(
    cancelListener: (() -> Unit)? = null,
    cancelText: String = stringResource(R.string.cancel),
    okListener: (() -> Unit)? = null,
    neutral: (@Composable () -> Unit)? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()
        val buttons = @Composable {
            if (neutral != null) {
                neutral()
                Spacer(Modifier.width(12.dp))
            }
            if (cancelListener != null) {
                OutlinedButton(
                    onClick = cancelListener,
                    modifier = modifier,
                ) {
                    Text(cancelText)
                }
            }
            if ((okListener != null) and (cancelListener != null)) Spacer(Modifier.width(12.dp))
            if (okListener != null) {
                Button(
                    onClick = okListener,
                    modifier = modifier,
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
        Row(Modifier.fillMaxWidth()) {
            buttons()
        }
    }
}
