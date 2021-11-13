package com.handysparksoft.groupsgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.handysparksoft.groupsgenerator.navigation.Navigation
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupsGeneratorApp {
                Navigation()
            }
        }
    }
}
