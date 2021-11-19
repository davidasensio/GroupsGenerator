package com.handysparksoft.groupsgenerator.ui.screens.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.getList
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.shared.ArrowBackIcon
import com.handysparksoft.groupsgenerator.ui.shared.BackToTopButton
import com.handysparksoft.groupsgenerator.ui.shared.Thumb
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(aListId: Int, onUpClick: () -> Unit) {
    val aList = remember { getList().first() { it.id == aListId } }
    val (newParticipant, setNewParticipant) = remember { mutableStateOf("") }
    val participants = rememberSaveable { mutableStateOf(aList.participants) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(aList.name) },
                navigationIcon = { ArrowBackIcon(onUpClick) })
        },
    ) { padding ->
        Column {
            ListBody(
                newParticipant = newParticipant,
                setNewParticipant = setNewParticipant,
                participants = participants,
                modifier = Modifier.weight(1f)
            )
            ListInfo(aList)
            GenerateGroupsButton()
        }
    }
}

@Composable
private fun ListInfo(aList: AList) {
    Box {
        Thumb(aList = aList)
        Text(
            text = "${aList.participants.size} Participants",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ListBody(
    newParticipant: String,
    setNewParticipant: (String) -> Unit,
    participants: MutableState<List<String>>,
    modifier: Modifier = Modifier
//    participants: List<String>
) {
    Column(modifier = modifier.padding(16.dp)) {
        ListAddRow(newParticipant, setNewParticipant, participants)
        ListParticipants(participants)
    }
}

@Composable
private fun ListAddRow(
    newParticipant: String,
    setNewParticipant: (String) -> Unit,
    participants: MutableState<List<String>>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = newParticipant,
            onValueChange = setNewParticipant,
            label = { Text(text = "Participant name") },
            placeholder = { Text(text = "Type participant's name") },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                participants.value = participants.value + newParticipant
                setNewParticipant("")
            },
//                    modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new participant"
            )
        }
    }
}

@Composable
private fun ListParticipants(
    participants: MutableState<List<String>>,
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showBackToTopButton = listState.firstVisibleItemIndex > 0


    Box(
//        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
//                Text(text = "Detail Screen $groupId", style = MaterialTheme.typography.h4)
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(participants.value) { participant ->
                AListItem(
                    participant = participant,
                    modifier = Modifier.fillMaxWidth()
                )
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
private fun AListItem(
    participant: String,
    modifier: Modifier = Modifier
) {
    Text(text = participant, modifier = modifier.padding(20.dp))
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
            aListId = 1,
            onUpClick = {}
        )
    }
}
