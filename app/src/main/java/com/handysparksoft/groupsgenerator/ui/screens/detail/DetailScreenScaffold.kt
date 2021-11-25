package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
                navigationIcon = { ArrowBackIcon(onUpClick) }
            )
        },
        content = content
    )
}
