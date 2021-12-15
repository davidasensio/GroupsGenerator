package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R

@Composable
fun MainBottomSheetContent(onSortAlphabetically: () -> Unit, onSortNumerically: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.main_sort_by),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        BottomSheetListItem(
            icon = Icons.Default.SortByAlpha,
            title = stringResource(id = R.string.main_sort_by_alpha),
            onItemClick = onSortAlphabetically
        )
        BottomSheetListItem(
            icon = Icons.Default.Sort,
            title = stringResource(id = R.string.main_sort_by_elements),
            onItemClick = onSortNumerically
        )
    }
}

@Composable
private fun BottomSheetListItem(icon: ImageVector, title: String, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .height(56.dp)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomSheetContentPreview() {
    MainBottomSheetContent(
        onSortAlphabetically = {},
        onSortNumerically = {}
    )
}
