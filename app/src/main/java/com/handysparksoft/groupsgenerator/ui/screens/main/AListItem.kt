package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.getList
import com.handysparksoft.groupsgenerator.ui.theme.C01Green
import com.handysparksoft.groupsgenerator.ui.theme.C02Orange
import com.handysparksoft.groupsgenerator.ui.theme.C03Red
import com.handysparksoft.groupsgenerator.ui.theme.C04Blue
import com.handysparksoft.groupsgenerator.ui.theme.C05Purple

@Composable
fun AListItem(aList: AList, onClick: () -> Unit) {
    Card(
        shape = CardCornerShape,
        elevation = 0.dp,
        backgroundColor = getListColor(aList.id),
        border = BorderStroke(1.dp, color = MaterialTheme.colors.secondary),
        modifier = Modifier
            .shadow(shape = CardCornerShape, elevation = 2.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .weight(1f)
            ) {
//                Thumb(aList)
                ListTitle(aList = aList)
                ListInfo(aList = aList)
            }
            /*Text(
                text = "${aList.participants.size} Participants",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 16.dp)
            )*/
            Image(
                painter = painterResource(id = R.drawable.il_team_work),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .width(60.dp)
            )
        }
    }
}

fun getListColor(listId: Int): Color =
    CardColors[listId % CardColors.size]

@Composable
private fun ListTitle(aList: AList) {
    Box() {
        Text(
            text = aList.name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
    }
}

@Composable
private fun ListInfo(aList: AList) {
    Box() {
        Text(
            text = "${aList.participants.size} participants",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
fun GroupItemPreview() {
    AListItem(aList = getList().first(), onClick = { })
}

private val CardCornerShape = CutCornerShape(16.dp, 0.dp, 16.dp, 0.dp)
private val CardColors = listOf(C01Green, C02Orange, C03Red, C04Blue, C05Purple)
