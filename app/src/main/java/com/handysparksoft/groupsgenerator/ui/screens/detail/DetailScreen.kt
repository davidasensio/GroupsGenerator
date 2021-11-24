package com.handysparksoft.groupsgenerator.ui.screens.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.Participant
import com.handysparksoft.groupsgenerator.model.ParticipantTypeIcon
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.shared.ArrowBackIcon
import com.handysparksoft.groupsgenerator.ui.shared.BackToTopButton
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(viewModel: DetailViewModel, onUpClick: () -> Unit) {
    val participants: List<Participant> = viewModel.participants

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewModel.listName) },
                navigationIcon = { ArrowBackIcon(onUpClick) })
        },
    ) { padding ->
        ParticipantsList(
            participants = participants,
            onAddParticipant = { viewModel.addParticipant(it) },
            onRemoveParticipant = { viewModel.removeParticipant(it) },
            currentEditingParticipant = viewModel.currentEditParticipant,
            onStartEdit = { viewModel.onEditParticipantSelected(it) },
            onEditDone = { viewModel.onEditDone() },
            onEditParticipantChange = { viewModel.onEditParticipantChange(it) },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun ParticipantsList(
    participants: List<Participant>,
    onAddParticipant: (Participant) -> Unit,
    onRemoveParticipant: (Participant) -> Unit,
    currentEditingParticipant: Participant?,
    onStartEdit: (Participant) -> Unit,
    onEditParticipantChange: (Participant) -> Unit,
    onEditDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        val enableHeader = currentEditingParticipant == null

        if (enableHeader) {
            ParticipantInputHeader(
                participantsNumber = participants.size,
                onAddParticipant = onAddParticipant,
            )
        } else {
            Surface(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f)) {
                Text(
                    text = "Editing participant",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))
        ListParticipants(
            participants = participants,
            onRemoveParticipant = onRemoveParticipant,
            currentEditingParticipant = currentEditingParticipant,
            onStartEdit = onStartEdit,
            onEditParticipantChange = onEditParticipantChange,
            onEditDone = onEditDone,
            modifier = Modifier.weight(1f)
        )
        ListInfo(participants)
        GenerateGroupsButton()
    }
}

@Composable
private fun ParticipantInputHeader(
    participantsNumber: Int,
    onAddParticipant: (Participant) -> Unit,
) {
    Column {
        ParticipantEntryItemInput(participantsNumber, onAddParticipant)
    }
}

@Composable
private fun ParticipantEntryItemInput(
    participantsNumber: Int,
    onAddParticipant: (Participant) -> Unit
) {
    val (text, setText) = rememberSaveable { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(ParticipantTypeIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onAddParticipant(Participant(id = participantsNumber + 1, name = text, icon = icon))
        setIcon(ParticipantTypeIcon.Default)
        setText("")
    }
    ParticipantItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        iconsVisible = iconsVisible,
        submit = submit,
        buttonSlot = {
            ParticipantAddButton(onClick = submit, isEnabled = text.isNotBlank())
        }
    )
}

@Composable
fun ParticipantItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: ParticipantTypeIcon,
    onIconChange: (ParticipantTypeIcon) -> Unit,
    iconsVisible: Boolean,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
            //.background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
        ) {
            ParticipantInputText(
                text = text,
                setText = onTextChange,
                submit = submit,
                modifier = Modifier.weight(1f)
            )
            buttonSlot()
        }
        // if (iconsVisible) {
        AnimatedIconRow(
            icon = icon,
            onIconChange = onIconChange,
            modifier = Modifier.padding(top = 8.dp),
            visible = iconsVisible
        )
        // } else {
        //     Spacer(modifier = Modifier.size(16.dp))
        // }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ParticipantInputText(
    text: String,
    setText: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = setText,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        label = { Text(text = "Participant name") },
        placeholder = { Text(text = "Type participant's name") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            submit()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
private fun ParticipantAddButton(onClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = isEnabled,
    ) {
        Row(verticalAlignment = CenterVertically) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Add")
        }
    }
}

@Composable
fun AnimatedIconRow(
    icon: ParticipantTypeIcon,
    onIconChange: (ParticipantTypeIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    Box(modifier = Modifier.defaultMinSize(16.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            IconRow(modifier, onIconChange, icon)
        }
    }
}

@Composable
private fun IconRow(
    modifier: Modifier,
    onIconChange: (ParticipantTypeIcon) -> Unit,
    icon: ParticipantTypeIcon
) {
    Row(modifier = modifier) {
        for (typeIcon in ParticipantTypeIcon.values()) {
            SelectableIconButton(
                icon = typeIcon.imageVector,
                iconContentDescription = typeIcon.contentDescription,
                onIconSelected = { onIconChange(typeIcon) },
                isSelected = typeIcon == icon
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    @StringRes iconContentDescription: Int,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) {
        MaterialTheme.colors.primaryVariant
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    }

    TextButton(onClick = { onIconSelected() }, modifier = modifier) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(id = iconContentDescription),
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ParticipantItemInlineEditor(
    participant: Participant,
    onEditParticipantChange: (Participant) -> Unit,
    onEditDone: () -> Unit,
    onRemoveParticipant: () -> Unit
) {
    ParticipantItemInput(
        text = participant.name,
        onTextChange = { onEditParticipantChange(participant.copy(name = it)) },
        icon = participant.icon,
        onIconChange = { onEditParticipantChange(participant.copy(icon = it)) },
        iconsVisible = participant.name.isNotBlank(),
        submit = onEditDone
    ) {
        Row() {
            IconButton(onClick = onEditDone, enabled = participant.name.isNotBlank()) {
                Text(text = "\uD83D\uDCBE") // Diskette
            }
            IconButton(onClick = onRemoveParticipant) {
                Text(text = "❌")
            }
        }
    }
}

@Composable
private fun ListParticipants(
    participants: List<Participant>,
    onRemoveParticipant: (Participant) -> Unit,
    currentEditingParticipant: Participant?,
    onStartEdit: (Participant) -> Unit,
    onEditParticipantChange: (Participant) -> Unit,
    onEditDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showBackToTopButton = listState.firstVisibleItemIndex > 0

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            items(participants) { participant ->
                if (participant.id == currentEditingParticipant?.id) {
                    ParticipantItemInlineEditor(
                        participant = participant,
                        onEditParticipantChange = onEditParticipantChange,
                        onEditDone = onEditDone,
                        onRemoveParticipant = { onRemoveParticipant(participant) }
                    )
                } else {
                    ParticipantRow(
                        participant = participant,
                        onParticipantClicked = onStartEdit,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
        BackToTopButton(
            showBackToTopButton = showBackToTopButton,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            scope.launch { listState.scrollToItem(0) }
        }
    }
}

@Composable
private fun ListInfo(participants: List<Participant>) {
    Box(Modifier.fillMaxWidth()) {
//        Thumb(aList = aList)
        Text(
            text = "${participants.size} Participants",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun GenerateGroupsButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Generate groups")
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailScreenPreview() {
    GroupsGeneratorApp {
        DetailScreen(
            viewModel = DetailViewModel(),
            onUpClick = {}
        )
    }
}
