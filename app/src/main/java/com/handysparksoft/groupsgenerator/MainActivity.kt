package com.handysparksoft.groupsgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.handysparksoft.groupsgenerator.navigation.Navigation
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailViewModel

class MainActivity : ComponentActivity() {
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupsGeneratorApp {
                Navigation(detailViewModel)
            }
        }
    }
}
