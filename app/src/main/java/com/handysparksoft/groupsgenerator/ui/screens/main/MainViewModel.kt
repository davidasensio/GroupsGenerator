package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.App
import com.handysparksoft.groupsgenerator.model.AList
import java.util.UUID

class MainViewModel : ViewModel() {
    var aLists = mutableStateListOf<AList>()
        private set

    var selectedALists = mutableStateListOf<String>()
        private set

    var fabTextShown = true
    var currentListPosition = 0
        set(value) {
            fabTextShown = value == 0 || value < currentListPosition
            field = value
        }
    var toolbarEditOptionsShown = mutableStateOf(false)
    var orderAsc = true

    init {
        loadSavedLists()
    }

    private fun loadSavedLists() {
        aLists.clear()
        val savedLists = App.prefs?.aLists ?: emptyList()
        aLists.addAll(savedLists)
    }

    fun addAList() {
        clearSelection()
        val aList = AList(
            id = UUID.randomUUID().toString(),
            name = "Random${aLists.size + 10}",
            description = "A fake description for this groupd...."
        )
        aLists.add(aList)
        saveToPrefs()
    }

    fun deleteSelected() {
        aLists.removeIf { selectedALists.contains(it.id) }
        clearSelection()
        updateToolbarEditOptionsVisibility()
        saveToPrefs()
        loadSavedLists()
    }

    fun addListToSelection(aListId: String) {
        selectedALists.add(aListId)
        updateToolbarEditOptionsVisibility()
    }

    fun removeListFromSelection(aListId: String) {
        selectedALists.remove(aListId)
        updateToolbarEditOptionsVisibility()
    }

    fun clearSelection() {
        selectedALists.clear()
        updateToolbarEditOptionsVisibility()
    }

    private fun updateToolbarEditOptionsVisibility() {
        toolbarEditOptionsShown.value = selectedALists.size > 0
    }

    fun sortAlphabetically() {
        if (orderAsc) {
            aLists.sortBy { it.name }
        } else {
            aLists.sortByDescending { it.name }
        }
        orderAsc = !orderAsc
    }

    private fun saveToPrefs() {
        App.prefs?.aLists = aLists
    }
}
