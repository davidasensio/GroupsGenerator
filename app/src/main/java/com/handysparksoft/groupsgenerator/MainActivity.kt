package com.handysparksoft.groupsgenerator

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.handysparksoft.groupsgenerator.navigation.Navigation
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailViewModel
import com.handysparksoft.groupsgenerator.ui.screens.generate.GenerateViewModel
import com.handysparksoft.groupsgenerator.ui.screens.main.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(App.prefs)
    }
    private val detailViewModel by viewModels<DetailViewModel>()
    private val generateViewModel by viewModels<GenerateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            GroupsGeneratorApp {
                Navigation(mainViewModel, detailViewModel, generateViewModel)
            }
        }
    }
}
