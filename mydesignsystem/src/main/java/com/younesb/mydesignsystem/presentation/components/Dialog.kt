package com.younesb.mydesignsystem.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.younesb.mydesignsystem.R

/**
 * A Material 3 dialog with built-in title, content, and action buttons.
 * Provides a simplified API for common dialog patterns while remaining highly customizable.
 *
 * Features:
 * - Auto-sizing with min/max width constraints
 * - Optional custom header or standard title with trailing content
 * - Integrated action buttons (OK, Cancel, Neutral) via [DialogButtons]
 * - Dismissible with custom dismiss behavior
 * - Material 3 styling with rounded corners
 *
 * The dialog is only shown when `visible` is true, making it easy to control with state.
 *
 * @param visible Whether the dialog should be displayed.
 * @param onDismissRequest Callback invoked when user attempts to dismiss (tap outside or back button).
 * @param header Optional custom header content. If provided, overrides title and trailingContent.
 * @param title The title text displayed at the top of the dialog (ignored if header is provided).
 * @param trailingContent Optional composable content displayed at the end of the title row.
 * @param centerTitle Whether to center-align the title text.
 * @param cancelListener Callback for cancel button. If null, cancel button is hidden.
 * @param cancelText Text for the cancel button (defaults to localized "Cancel").
 * @param okListener Callback for OK button. If null, OK button is hidden.
 * @param neutral Optional composable for a neutral action button (e.g., "Learn More").
 * @param content The main content of the dialog, provided as a ColumnScope composable.
 *
 * @sample
 * ```
 * var showDialog by remember { mutableStateOf(false) }
 *
 * Dialog(
 *     visible = showDialog,
 *     onDismissRequest = { showDialog = false },
 *     title = "Confirm Action",
 *     cancelListener = { showDialog = false },
 *     okListener = {
 *         performAction()
 *         showDialog = false
 *     }
 * ) {
 *     Text(
 *         "Are you sure you want to continue?",
 *         modifier = Modifier.padding(horizontal = 24.dp)
 *     )
 * }
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    title: String,
    trailingContent: @Composable (RowScope.() -> Unit)? = null,
    centerTitle: Boolean = false,
    cancelListener: (() -> Unit)? = null,
    cancelText: String = stringResource(R.string.cancel),
    okListener: (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit),
) {
    if (visible) {
        BasicAlertDialog(
            onDismissRequest = onDismissRequest,
            modifier =
                Modifier
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(24.dp),
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .clipToBounds()
                    .fillMaxWidth(0.9f)
                    .widthIn(min = 280.dp, max = 560.dp),
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Surface(
                contentColor = MaterialTheme.colorScheme.onBackground,
                color = Color.Transparent,
            ) {
                Column(Modifier.fillMaxWidth()) {
                    if (header == null) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                textAlign = if (centerTitle) TextAlign.Center else null,
                            )
                            if (trailingContent != null) {
                                trailingContent(this)
                            }
                        }
                    } else {
                        header(this)
                    }
                    content(this)
                    DialogButtons(cancelListener, cancelText, okListener, neutral)
                }
            }
        }
    }
}
