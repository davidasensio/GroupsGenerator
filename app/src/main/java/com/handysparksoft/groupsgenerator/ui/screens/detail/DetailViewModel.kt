package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.App
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.Participant
import com.handysparksoft.groupsgenerator.model.ParticipantTypeIcon
import java.util.UUID as UniqueIdentifier

class DetailViewModel : ViewModel() {

    lateinit var aList: AList

    var aListId: String? = null
        set(value) {
            field = value
            selectAList()
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

    private var orderAsc = true
    private fun selectAList() {
        App.prefs?.aLists?.firstOrNull { it.id == aListId }?.let {
            aList = it
            participants.clear()
            participants.addAll(aList.participants)
        }
    }

    fun addParticipants(names: String, icon: ParticipantTypeIcon) {
        val inputNames = names.split(BREAK_LINE)
        inputNames.forEach { itemName ->
            addParticipant(
                Participant(
                    id = UniqueIdentifier.randomUUID().toString(),
                    name = itemName,
                    icon = icon,
                    isCouple = icon == ParticipantTypeIcon.Couple,
                    isDeactivated = icon == ParticipantTypeIcon.Deactivated
                )
            )
        }
        saveToPrefs()
    }

    fun addParticipant(participant: Participant) {
        participants.add(participant)
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

    fun sortAlphabetically() {
        if (orderAsc) {
            participants.sortBy { it.name }
        } else {
            participants.sortByDescending { it.name }
        }
        orderAsc = !orderAsc
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

    companion object {
        private const val BREAK_LINE = "\n"
    }
}
