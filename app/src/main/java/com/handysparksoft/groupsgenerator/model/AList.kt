package com.handysparksoft.groupsgenerator.model

import androidx.annotation.DrawableRes
import com.handysparksoft.groupsgenerator.model.AList.Type.Normal
import java.io.Serializable

data class AList(
    val id: String,
    val name: String,
    val description: String = "",
    val participants: List<Participant> = emptyList(),
    val type: Type = Normal,
    @DrawableRes val image: Int? = null
) : Serializable {
    enum class Type { Normal, Special }

    val itemCount = participants.size
    val itemRealCount = participants.sumOf { it.countValue }
}

data class Participant(
    val id: String,
    val name: String,
    val isCouple: Boolean = false,
    val isHead: Boolean = false,
    val isDeactivated: Boolean = false,
    val icon: ParticipantTypeIcon = ParticipantTypeIcon.Default
) {
    val countValue
        get() = when {
            isDeactivated -> 0
            isCouple -> 2
            else -> 1
        }
}
