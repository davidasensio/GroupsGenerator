package com.handysparksoft.groupsgenerator.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.ui.graphics.vector.ImageVector
import com.handysparksoft.groupsgenerator.R

enum class ParticipantTypeIcon(
    val imageVector: ImageVector,
    @StringRes val contentDescription: Int
) {
    Participant(Icons.Default.Person, R.string.type_participant),
    Couple(Icons.Default.People, R.string.type_couple),
    Deactivated(Icons.Default.PersonOff, R.string.type_deactivated);
    //Head(Icons.Default.Psychology, R.string.type_head),

    companion object {
        val Default = Participant
    }
}
