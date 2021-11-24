package com.handysparksoft.groupsgenerator.model

import androidx.annotation.DrawableRes
import com.handysparksoft.groupsgenerator.model.AList.Type.Normal
import java.io.Serializable

data class AList(
    val id: Int,
    val name: String,
    val description: String?,
    val participants: List<Participant> = emptyList(),
    val type: Type = Normal,
    @DrawableRes val image: Int? = null
) : Serializable {
    enum class Type { Normal, Special }
}

data class Participant(
    val id: Int,
    val name: String,
    val isCouple: Boolean = false,
    val isHead: Boolean = false,
    val isDeactivated: Boolean = false,
    val icon: ParticipantTypeIcon = ParticipantTypeIcon.Default
)
