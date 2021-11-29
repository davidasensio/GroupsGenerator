package com.handysparksoft.groupsgenerator.ui.screens.generate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.ui.shared.ArrowBackIcon

@Composable
fun GenerateScreenScaffold(
    onUpClick: () -> Unit,
    onCopyGroupsClick: () -> Unit,
    onShareGeneratedGroupsClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.generate_groups_title)) },
                navigationIcon = { ArrowBackIcon(onUpClick) },
                actions = {
                    GenerateAppBarOverflowMenu(
                        onCopyGroupsClick = onCopyGroupsClick,
                        onShareGeneratedGroupsClick = onShareGeneratedGroupsClick
                    )
                }
            )
        },
        content = content
    )
}
