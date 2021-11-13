package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.handysparksoft.groupsgenerator.model.getGroups
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.common.Thumb

@Composable
fun DetailScreen(id: Int) {
    val group = remember { getGroups().first() { it.id == id } }

    Scaffold(topBar = { TopAppBar(title = { Text(group.name) }) }) { padding ->
        Thumb(group = group, modifier = Modifier.padding(padding))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Detail Screen $id", style = MaterialTheme.typography.h4)
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    GroupsGeneratorApp {
        DetailScreen(1)
    }
}
