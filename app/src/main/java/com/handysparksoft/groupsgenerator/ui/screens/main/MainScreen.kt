package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { MainAppBar() }
    ) {
        Text(text = "Content", modifier = Modifier.padding(it))
    }
}
