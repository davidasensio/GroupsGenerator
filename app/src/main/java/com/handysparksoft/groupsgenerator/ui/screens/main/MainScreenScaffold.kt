package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreenScaffold(viewModel: MainViewModel, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = { MainAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addAList() }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add a list",
                    )
                    AnimatedVisibility(visible = viewModel.fabTextShown) {
                        Text(text = "Add new list")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = content
    )
}
