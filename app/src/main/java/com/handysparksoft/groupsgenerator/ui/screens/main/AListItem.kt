package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.ui.theme.C01Green
import com.handysparksoft.groupsgenerator.ui.theme.C01GreenDark
import com.handysparksoft.groupsgenerator.ui.theme.C02Orange
import com.handysparksoft.groupsgenerator.ui.theme.C02OrangeDark
import com.handysparksoft.groupsgenerator.ui.theme.C03Red
import com.handysparksoft.groupsgenerator.ui.theme.C03RedDark
import com.handysparksoft.groupsgenerator.ui.theme.C04Blue
import com.handysparksoft.groupsgenerator.ui.theme.C04BlueDark
import com.handysparksoft.groupsgenerator.ui.theme.C05Purple
import com.handysparksoft.groupsgenerator.ui.theme.C05PurpleDark
import com.handysparksoft.groupsgenerator.ui.theme.C06Pink
import com.handysparksoft.groupsgenerator.ui.theme.C06PinkDark
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AListItem(
    aList: AList,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = CardCornerShape,
        elevation = 0.dp,
        backgroundColor = getListColor(aList.id, isSystemInDarkTheme()),
        border = getBorder(selected),
        modifier = Modifier
            .shadow(shape = CardCornerShape, elevation = if (selected) 8.dp else 1.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { onSelectedChange(!selected) }
            )
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .weight(1f)
            ) {
//                Thumb(aList)
                ListTitle(title = aList.name)
                ListDescription(description = aList.description)
                ListInfo(aList = aList)
            }
            Image(
                painter = painterResource(id = aList.image),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 24.dp)
                    .width(60.dp)
            )
        }
    }
}

@Composable
private fun getBorder(selected: Boolean) = when (selected) {
    true -> BorderStroke(3.dp, color = MaterialTheme.colors.primaryVariant)
    false -> BorderStroke(1.dp, color = MaterialTheme.colors.primary)
}

fun getListColor(listId: String, systemInDarkTheme: Boolean): Color = when (systemInDarkTheme) {
    true -> CardColorsDark[abs(listId.hashCode()) % CardColors.size]
    false -> CardColors[abs(listId.hashCode()) % CardColors.size]
}

@Composable
private fun ListTitle(title: String) {
    Box() {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
    }
}

@Composable
private fun ListDescription(description: String) {
    Box() {
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            maxLines = 3,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ListInfo(aList: AList) {
    Box() {
        Text(
            text = stringResource(id = R.string.number_participants, aList.participants.size),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
fun GroupItemPreview() {
    AListItem(
        aList = AList(
            id = "1",
            name = "List 1",
            description = "The first list"
        ),
        selected = false,
        onSelectedChange = { },
        onClick = { }
    )
}

private val CardCornerShape = CutCornerShape(24.dp, 0.dp, 24.dp, 0.dp)
private val CardColors = listOf(
    C01Green,
    C02Orange,
    C03Red,
    C04Blue,
    C05Purple,
    C06Pink
)
private val CardColorsDark = listOf(
    C01GreenDark,
    C02OrangeDark,
    C03RedDark,
    C04BlueDark,
    C05PurpleDark,
    C06PinkDark
)
