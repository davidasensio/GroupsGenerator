package com.handysparksoft.groupsgenerator.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen
import com.handysparksoft.groupsgenerator.ui.theme.GroupsGeneratorTheme

@Composable
fun GroupsGeneratorApp(content: @Composable () -> Unit) {
    GroupsGeneratorTheme {
        Surface {
            content()
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GroupsGeneratorPreview() {
    GroupsGeneratorApp {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            MainScreen(onAListClick = { })
        }
    }
}
