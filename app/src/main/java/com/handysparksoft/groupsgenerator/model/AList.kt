package com.handysparksoft.groupsgenerator.model

import androidx.annotation.DrawableRes
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.AList.Type.Normal
import com.handysparksoft.groupsgenerator.model.AList.Type.Special
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

fun getList() = (1..6).map {
    AList(
        id = it,
        name = "Group $it",
        description = "This is the group number $it",
        type = if (it % 3 == 0) Special else Normal,
        image = R.drawable.ic_launcher_background,
    )
}
