package com.handysparksoft.groupsgenerator.ui.screens.generate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import com.handysparksoft.groupsgenerator.ui.shared.AppBarAction

@Composable
fun GenerateAppBarOverflowMenu(
    onCopyGroupsClick: () -> Unit,
    onShareGeneratedGroupsClick: () -> Unit
) {
    AppBarAction(
        imageVector = Icons.Default.ContentCopy,
        onClick = onCopyGroupsClick,
        contentDescription = "Copy groups"
    )
    AppBarAction(
        imageVector = Icons.Default.Share,
        onClick = onShareGeneratedGroupsClick,
        contentDescription = "Share groups"
    )
}
