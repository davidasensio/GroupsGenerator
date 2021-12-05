package com.handysparksoft.groupsgenerator.ui.screens.generate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.ui.shared.AppBarAction

@Composable
fun GenerateAppBarOverflowMenu(
    onCopyGroupsClick: (copiedMessage: String) -> Unit,
    onShareGeneratedGroupsClick: () -> Unit
) {
    val copiedMessage = stringResource(id = R.string.generate_copied_to_clipboard)
    AppBarAction(
        imageVector = Icons.Default.ContentCopy,
        onClick = { onCopyGroupsClick(copiedMessage) },
        contentDescription = stringResource(R.string.generate_copy_groups)
    )
    AppBarAction(
        imageVector = Icons.Default.Share,
        onClick = onShareGeneratedGroupsClick,
        contentDescription = stringResource(R.string.generate_share_groups)
    )
}
