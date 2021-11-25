package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBarOverflowMenu(
    anySelected: Boolean,
    onDeleteClick: () -> Unit,
    onSortClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit
) {
    if (anySelected) {
        AppBarAction(
            imageVector = Icons.Default.Delete,
            onClick = onDeleteClick,
            contentDescription = "Delete List"
        )
    } else {
        var showMenu by remember { mutableStateOf(false) }
        AppBarAction(
            imageVector = Icons.Default.SortByAlpha,
            onClick = onSortClick,
            contentDescription = "Sort alphabetically"
        )
        IconButton(onClick = { showMenu = !showMenu }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More options")
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = onShareClick) {
                    ListItem(
                        text = { Text(text = "Share") },
                        trailing = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        },
                        modifier = Modifier.defaultMinSize(140.dp)
                    )
                }
                DropdownMenuItem(onClick = onRateClick) {
                    ListItem(
                        text = { Text(text = "Rate") },
                        trailing = {
                            Icon(
                                imageVector = Icons.Default.StarRate,
                                contentDescription = "Rate"
                            )
                        },
                        modifier = Modifier.defaultMinSize(140.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppBarAction(
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}
