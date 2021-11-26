package com.handysparksoft.groupsgenerator.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(onUpClick: () -> Unit, onCreateClick: (AList) -> Unit) {
    CreateScreenScaffold(onUpClick = onUpClick) {

        val (name, setName) = remember { mutableStateOf("") }
        val (description, setDescription) = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = setName,
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Type the list name") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (name.isNotBlank()) {
                                keyboardController?.hide()
                            }
                        }
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = setDescription,
                    label = { Text(text = "Description") },
                    placeholder = { Text(text = "Type a description for this list") },
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (name.isNotBlank()) {
                                keyboardController?.hide()
                            }
                        }
                    )
                )
            }
            Button(
                onClick = {
                    onCreateClick(
                        AList(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            description = description
                        )
                    )
                },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create")
            }
        }
    }
}

@Preview
@Composable
fun CreateAListPreview() {
    GroupsGeneratorApp {
        CreateScreen(onUpClick = {}, onCreateClick = {})
    }
}
