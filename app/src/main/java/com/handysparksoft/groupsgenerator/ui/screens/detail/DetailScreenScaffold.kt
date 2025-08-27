package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.handysparksoft.groupsgenerator.ui.shared.AppBarAction
import com.handysparksoft.groupsgenerator.ui.shared.ArrowBackIcon

@Composable
fun DetailScreenScaffold(
    viewModel: DetailViewModel,
    onUpClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewModel.listName) },
                navigationIcon = { ArrowBackIcon(onUpClick) },
                actions = {
                    AppBarAction(
                        imageVector = Icons.Default.SortByAlpha,
                        onClick = { viewModel.sortAlphabetically() }
                    )
                },
                modifier = Modifier.statusBarsPadding()
            )
        },
        content = content
    )
}
