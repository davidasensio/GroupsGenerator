package com.handysparksoft.groupsgenerator.ui.screens.main

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.platform.ShareIntentHandler

@Composable
fun MainScreenScaffold(
    viewModel: MainViewModel,
    onCreateClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            MainAppBar(
                anySelected = viewModel.toolbarDeleteOptionShown.value,
                onSortClick = {
                    viewModel.sortAlphabetically()
                },
                onDeleteClick = {
                    viewModel.showDeleteConfirmDialog.value = true
                },
                onShareClick = {
                    (context as? Activity)?.let {
                        ShareIntentHandler.showShareAppIntentChooser(it)
                    }
                },
                onRateClick = {
                    (context as? Activity)?.let {
                        ShareIntentHandler.rateAppInGooglePlayIntent(it)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateClick) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.main_add_new_list),
                    )
                    AnimatedVisibility(visible = viewModel.fabTextShown) {
                        Text(text = stringResource(R.string.main_add_new_list))
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = content
    )
}
