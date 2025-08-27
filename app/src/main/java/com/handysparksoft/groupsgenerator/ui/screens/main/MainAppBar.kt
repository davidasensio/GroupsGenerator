package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R

@Composable
fun MainAppBar(
    anySelected: Boolean,
    onSortClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
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
