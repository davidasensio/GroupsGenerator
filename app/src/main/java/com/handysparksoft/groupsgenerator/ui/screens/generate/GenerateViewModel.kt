package com.handysparksoft.groupsgenerator.ui.screens.generate

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.handysparksoft.groupsgenerator.App
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.Participant

class GenerateViewModel : ViewModel() {
    lateinit var aList: AList

    var aListId: String? = null
        set(value) {
            field = value
            selectAList()
        }

    var generatedGroups = mutableStateListOf<MutableList<Participant>>()
        private set

    val listName: String
        get() = if (::aList.isInitialized) {
            aList.name
        } else ""

    var participants = mutableStateListOf<Participant>()
        private set

    private fun selectAList() {
        App.prefs?.aLists?.firstOrNull { it.id == aListId }?.let {
            aList = it
            participants.clear()
            participants.addAll(aList.participants)
        }
    }

    fun generateRandomGroups(mode: Int, elementsNumber: Int) {
        val participants = aList.participants.filter { !it.isDeactivated }
        val realParticipantsSize = aList.itemRealCount
        val elementsPerGroup = when (mode) {
            MODE_GROUPS -> realParticipantsSize / elementsNumber
            else -> elementsNumber
        }
        val maxGroups = when (mode) {
            MODE_GROUPS -> elementsNumber
            else -> participants.size
        }
        val currentGroup = mutableListOf<Participant>()
        var counter = 0

        generatedGroups.clear()

        participants.shuffled().forEach {
            val neededNewGroup = counter >= elementsPerGroup && generatedGroups.size < maxGroups
            if (neededNewGroup) {
                generatedGroups.add(currentGroup.toMutableList())
                currentGroup.clear()
                counter = 0
            }
            currentGroup.add(it)
            counter += it.countValue
        }

        // Add the widow elements
        if (currentGroup.size > 0) {
            val addWidowedToNewGroup = mode == MODE_ELEMENTS || generatedGroups.size < elementsNumber
            if (addWidowedToNewGroup) {
                generatedGroups.add(currentGroup)
            } else {
                generatedGroups.last().addAll(currentGroup)
            }
        }
    }

    fun getContent(): String {
        val result = StringBuilder()
        generatedGroups.forEachIndexed { index, group ->
            result.appendLine().append("- Group ${index + 1} -").appendLine()
            group.forEach {
                result.appendLine(it.name)
            }
        }
        return result.toString()
    }

    companion object {
        private const val MODE_GROUPS = 0
        private const val MODE_ELEMENTS = 1
    }
}
