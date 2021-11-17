package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.Group
import com.handysparksoft.groupsgenerator.model.getGroups
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp

@Composable
fun MainScreen(onGroupClick: (Group) -> Unit) {
    Scaffold(
        topBar = { MainAppBar() }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(getGroups()) { group ->
                GroupItem(group, onClick = { onGroupClick(group) })
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    GroupsGeneratorApp {
        MainScreen(onGroupClick = {})
    }
}
