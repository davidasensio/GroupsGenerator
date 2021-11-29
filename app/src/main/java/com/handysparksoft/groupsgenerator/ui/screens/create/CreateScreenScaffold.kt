package com.handysparksoft.groupsgenerator.ui.screens.create

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.ui.shared.ArrowBackIcon

@Composable
fun CreateScreenScaffold(
    onUpClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_create_new_list_title)) },
                navigationIcon = { ArrowBackIcon(onUpClick) }
            )
        },
        content = content
    )
}
