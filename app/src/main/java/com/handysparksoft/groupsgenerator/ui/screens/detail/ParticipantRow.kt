package com.handysparksoft.groupsgenerator.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.Participant
import kotlin.random.Random

@Composable
fun ParticipantRow(
    participant: Participant,
    onParticipantClicked: (Participant) -> Unit,
    modifier: Modifier,
    iconAlpha: Float = remember(participant.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onParticipantClicked(participant) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = participant.name)
        Icon(
            imageVector = participant.icon.imageVector,
            contentDescription = stringResource(id = participant.icon.contentDescription),
            // tint = LocalContentColor.current.copy(alpha = iconAlpha)
        )
    }
}

private fun randomTint(): Float = Random.nextFloat().coerceIn(0.3f, 0.9f)

@Preview
@Composable
fun ParticipantRowPreview() {
    ParticipantRow(
        participant = Participant("1", "David Bowie"),
        onParticipantClicked = {},
        modifier = Modifier
    )
}
