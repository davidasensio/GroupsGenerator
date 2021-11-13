package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.handysparksoft.groupsgenerator.model.Group
import com.handysparksoft.groupsgenerator.ui.common.Thumb

@Composable
fun GroupItem(group: Group, navController: NavController?) {
    Column(modifier = Modifier.clickable { navController?.navigate("detail/${group.id}") }) {
        Thumb(group)
        Title(group)
    }
}

@Composable
private fun Title(group: Group) {
    Box() {
        Text(
            text = group.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
                .padding(16.dp)
        )
    }
}
