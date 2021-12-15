package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.data.Prefs
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.shared.ConfirmDialog

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onAListClick: (AList) -> Unit,
    onCreateClick: () -> Unit,
    onSortClick: () -> Unit
) {
    MainScreenScaffold(
        viewModel = viewModel,
        onCreateClick = onCreateClick,
        onSortClick = onSortClick
    ) { padding ->
        MainScreenContent(padding, viewModel, onAListClick)

        if (viewModel.showDeleteConfirmDialog.value) {
            ConfirmDialog(
                showDialog = viewModel.showDeleteConfirmDialog.value,
                onShowDialogChange = { viewModel.showDeleteConfirmDialog.value = it },
                title = stringResource(R.string.action_confirm),
                text = stringResource(R.string.question_delete_selected_lists),
                onConfirmClick = { viewModel.deleteSelected() },
                onDismissClick = { viewModel.showDeleteConfirmDialog.value = false }
            )
        }
    }
}

@Composable
private fun MainScreenContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    onAListClick: (AList) -> Unit
) {
    val listState = rememberLazyListState()
    viewModel.currentListPosition = listState.firstVisibleItemIndex

    Column {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .weight(1f),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(viewModel.aLists, key = { it.id }) { aList ->
                val (selected, setSelected) = rememberSaveable(aList.id) {
                    mutableStateOf(false)
                }
                AListItem(
                    aList = aList,
                    selected = selected,
                    onSelectedChange = { isSelected ->
                        setSelected(isSelected)
                        viewModel.addListToSelection(aList.id)
                    },
                    onClick = {
                        if (selected) {
                            setSelected(false)
                            viewModel.removeListFromSelection(aList.id)
                        } else {
                            viewModel.clearSelection()
                            onAListClick(aList)
                        }
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = viewModel.showEmptyView.value,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 36.dp, end = 100.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.main_create_your_first_list),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.8f)
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_hand_arrow),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    GroupsGeneratorApp {
        MainScreen(
            viewModel = MainViewModel(Prefs(LocalContext.current)),
            onAListClick = {},
            onCreateClick = {},
            onSortClick = {}
        )
    }
}
