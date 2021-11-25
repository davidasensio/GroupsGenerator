package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainAppBar(
    anySelected: Boolean,
    onSortClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            AppBarOverflowMenu(
                anySelected = anySelected,
                onDeleteClick = onDeleteClick,
                onSortClick = onSortClick,
                onShareClick = onShareClick,
                onRateClick = onRateClick
            )
        }
    )
}
