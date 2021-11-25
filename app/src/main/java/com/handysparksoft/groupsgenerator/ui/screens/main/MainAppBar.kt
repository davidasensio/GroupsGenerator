package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R

@Composable
fun MainAppBar(
    anySelected: Boolean,
    onSortClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            if (anySelected) {
                AppBarAction(imageVector = Icons.Default.Delete, onClick = onDeleteClick)
            } else {
                AppBarAction(imageVector = Icons.Default.SortByAlpha, onClick = onSortClick)
                AppBarAction(imageVector = Icons.Default.MoreVert, onClick = { /*TODO*/ })
            }
        }
    )
}

@Composable
private fun AppBarAction(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}
