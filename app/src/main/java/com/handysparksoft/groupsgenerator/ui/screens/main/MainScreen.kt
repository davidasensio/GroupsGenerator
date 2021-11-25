package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp

@Composable
fun MainScreen(viewModel: MainViewModel, onAListClick: (AList) -> Unit) {
    val listState = rememberLazyListState()
    viewModel.currentListPosition = listState.firstVisibleItemIndex

    MainScreenScaffold(viewModel = viewModel) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(viewModel.aLists) { group ->
                AListItem(group, onClick = { onAListClick(group) })
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    GroupsGeneratorApp {
        MainScreen(
            viewModel = MainViewModel(),
            onAListClick = {}
        )
    }
}
