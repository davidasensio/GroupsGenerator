package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.App
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.Participant

class DetailViewModel : ViewModel() {

    lateinit var aList: AList

    var aListId: String? = null
        set(value) {
            field = value
            selectAList()
        }

    private fun selectAList() {
        App.prefs?.aLists?.firstOrNull { it.id == aListId }?.let {
            aList = it
            participants.clear()
            participants.addAll(aList.participants)
        }
    }

    val listName: String
        get() = if (::aList.isInitialized) {
            aList.name
        } else ""

    private var currentEditPosition by mutableStateOf(-1)
    var participants = mutableStateListOf<Participant>()
        private set

    val currentEditParticipant: Participant?
        get() = participants.getOrNull(currentEditPosition)

    fun addParticipant(participant: Participant) {
        participants.add(participant)
        saveToPrefs()
    }

    fun removeParticipant(participant: Participant) {
        participants.remove(participant)
        onEditDone()
        saveToPrefs()
    }

    fun onEditParticipantSelected(participant: Participant) {
        currentEditPosition = participants.indexOf(participant)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    fun onEditParticipantChange(participant: Participant) {
        val currentItem = requireNotNull(currentEditParticipant)
        require(currentItem.id == participant.id) {
            "Edited participant id should be the same as the current one"
        }
        participants[currentEditPosition] = participant
        saveToPrefs()
    }

    private fun saveToPrefs() {
        App.prefs?.aLists?.let {
            val newList = aList.copy(participants = participants)
            val newLists = it.toMutableList()
            newLists[it.indexOf(aList)] = newList
            App.prefs?.aLists = newLists
            selectAList()
        }
    }
}
