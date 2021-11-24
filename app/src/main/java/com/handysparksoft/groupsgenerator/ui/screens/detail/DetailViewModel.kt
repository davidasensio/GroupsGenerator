package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.Participant
import com.handysparksoft.groupsgenerator.model.getList

class DetailViewModel : ViewModel() {
    lateinit var aList: AList
    var aListId: Int? = null
        set(value) {
            field = value
            aList = getList().first() { it.id == aListId }
        }
    val listName: String
        get() = aList.name

    //    private val _participants = MutableLiveData(mutableListOf<Participant>())
//    val participants: LiveData<MutableList<Participant>> = _participants
    private var currentEditPosition by mutableStateOf(-1)
    var participants = mutableStateListOf<Participant>()
        private set
    val currentEditParticipant: Participant?
        get() = participants.getOrNull(currentEditPosition)

    init {
        aList = getList()[0]
//        participants.addAll(aList.participants) //.toMutableList()
    }

    fun addParticipant(participant: Participant) {
        participants.add(participant)
    }

    fun removeParticipant(participant: Participant) {
        participants.remove(participant)
        onEditDone()
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
    }
}
