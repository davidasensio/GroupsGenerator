package com.handysparksoft.groupsgenerator.ui.screens.main

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.handysparksoft.groupsgenerator.data.Prefs
import com.handysparksoft.groupsgenerator.model.AList

class MainViewModel(private val prefs: Prefs?) : ViewModel() {
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
    var toolbarDeleteOptionShown = mutableStateOf(false)
    var showDeleteConfirmDialog = mutableStateOf(false)
    var showEmptyView = derivedStateOf { aLists.isEmpty() }
    private var orderAsc = true

    init {
        loadSavedLists()
    }

    fun getLists() = aLists.toList()

    private fun setLists(theLists: List<AList>) {
        aLists.clear()
        aLists.addAll(theLists)
    }

    fun loadSavedLists() {
        clearSelection()
        val savedLists = prefs?.aLists ?: emptyList()
        setLists(savedLists)
    }

    fun addAList(aList: AList) {
        aLists.add(aList)
        saveToPrefs()
        loadSavedLists()
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
        toolbarDeleteOptionShown.value = selectedALists.size > 0
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
        prefs?.aLists = aLists
    }

    companion object {
        // Factory for MainViewModel that takes Preferences as a dependency
        fun provideFactory(prefs: Prefs?) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(prefs) as T
            }
        }
    }
}
