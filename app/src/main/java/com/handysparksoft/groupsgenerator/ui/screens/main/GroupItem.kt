package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.Group
import com.handysparksoft.groupsgenerator.model.Group.Type.Special
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp

@Composable
fun GroupItem(group: Group) {
    Column {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = group.image ?: R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            if (group.type == Special) {
                Image(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
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
}

@Preview
@Composable
fun GroupItemPreview() {
    GroupsGeneratorApp {
        GroupItem(
            group = Group(
                1,
                name = "Group 1",
                description = "First group",
                type = Group.Type.Normal,
                image = R.drawable.ic_launcher_background
            )
        )
    }
}
