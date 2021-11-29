package com.handysparksoft.groupsgenerator.ui.shared

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.handysparksoft.groupsgenerator.R

@Composable
fun ConfirmDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    title: String,
    text: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    confirmButtonText: String = stringResource(R.string.dialog_accept),
    dismissButtonText: String = stringResource(R.string.dialog_dismiss),
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onShowDialogChange(false) },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmClick()
                        onDismissClick()
                    }
                ) {
                    Text(text = confirmButtonText, style = MaterialTheme.typography.button)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissClick) {
                    Text(text = dismissButtonText, style = MaterialTheme.typography.button)
                }
            }
        )
    }
}

@Preview
@Composable
fun ConfirmDialogPreview() {
    ConfirmDialog(
        showDialog = true,
        onShowDialogChange = {},
        title = "Preview",
        text = "Sample preview text",
        onConfirmClick = { },
        onDismissClick = { }
    )
}
