package com.handysparksoft.groupsgenerator.model

import androidx.annotation.DrawableRes
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.Group.Type.Normal
import com.handysparksoft.groupsgenerator.model.Group.Type.Special
import java.io.Serializable

data class Group(
    val id: Int,
    val name: String,
    val description: String?,
    val type: Type = Normal,
    @DrawableRes val image: Int?
): Serializable {
    enum class Type { Normal, Special }
}

fun getGroups() = (1..10).map {
    Group(
        id = it,
        name = "Group $it",
        description = "This is the group number $it",
        type = if (it % 3 == 0) Special else Normal,
        image = R.drawable.ic_launcher_background
    )
}
