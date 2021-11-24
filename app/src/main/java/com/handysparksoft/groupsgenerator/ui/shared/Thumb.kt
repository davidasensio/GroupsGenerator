package com.handysparksoft.groupsgenerator.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.AList.Type.Special

@Composable
fun Thumb(aList: AList, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxSize()
    ) {
//        Image(
//            modifier = Modifier.fillMaxSize(),
//            painter = painterResource(
//                id = aList.image ?: R.drawable.ic_launcher_background
//            ),
//            contentScale = ContentScale.Crop,
//            contentDescription = null
//        )
        if (aList.type == Special) {
            Image(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}
