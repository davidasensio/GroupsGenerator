package com.handysparksoft.groupsgenerator.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R

@Composable
fun BackToTopButton(
    showBackToTopButton: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = showBackToTopButton,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        OutlinedButton(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
            onClick = onClick,
            modifier = Modifier.height(48.dp)
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
            Text(text = stringResource(R.string.action_back_to_top))
        }
    }
}
