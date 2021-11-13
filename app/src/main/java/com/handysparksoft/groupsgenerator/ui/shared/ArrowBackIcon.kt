package com.handysparksoft.groupsgenerator.ui.shared

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.handysparksoft.groupsgenerator.R

@Composable
fun ArrowBackIcon(onUpClick: () -> Unit) {
    IconButton(onClick = onUpClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(
                id = R.string.up_action_top_bar
            )
        )
    }
}
