package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.App
import com.handysparksoft.groupsgenerator.model.AList

class MainViewModel : ViewModel() {
    var aLists = mutableStateListOf<AList>()
        private set

    init {
        val savedLists = App.prefs?.aLists ?: emptyList()
        aLists.addAll(savedLists)
    }

    fun addAList() {
        val aList = AList(
            id = aLists.size + 1,
            name = "Random${aLists.size + 10}",
            description = "ddd"
        )
        aLists.add(aList)

        saveToPrefs()
    }

    private fun saveToPrefs() {
        App.prefs?.aLists = aLists
    }
}
