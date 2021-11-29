package com.handysparksoft.groupsgenerator.ui.screens.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import java.util.UUID as UniqueIdentifier

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(onUpClick: () -> Unit, onCreateClick: (AList) -> Unit) {
    CreateScreenScaffold(onUpClick = onUpClick) {

        val (name, setName) = rememberSaveable { mutableStateOf("") }
        val (description, setDescription) = rememberSaveable { mutableStateOf("") }
        val (illustrationIndex, setIllustrationIndex) = rememberSaveable { mutableStateOf(0) }
        val (illustration, setIllustration) = rememberSaveable {
            mutableStateOf(illustrations.last())
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = setName,
                    label = { Text(text = stringResource(R.string.create_name_label)) },
                    placeholder = { Text(text = stringResource(R.string.create_name_placeholder)) },
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
                    label = { Text(text = stringResource(R.string.create_description_label)) },
                    placeholder = {
                        Text(text = stringResource(R.string.create_description_placeholder))
                    },
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
                Image(
                    painter = painterResource(id = illustration),
                    contentDescription = stringResource(R.string.create_list_illustration),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .size(300.dp)
                        .padding(32.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            setIllustrationIndex(illustrationIndex + 1)
                            setIllustration(illustrations[illustrationIndex % illustrations.size])
                        }
                )
            }
            Button(
                onClick = {
                    onCreateClick(
                        AList(
                            id = UniqueIdentifier.randomUUID().toString(),
                            name = name,
                            description = description,
                            image = illustration
                        )
                    )
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(stringResource(R.string.action_create))
            }
        }
    }
}

private val illustrations = ListIllustration.illustrations

@Preview
@Composable
fun CreateAListPreview() {
    GroupsGeneratorApp {
        CreateScreen(onUpClick = {}, onCreateClick = {})
    }
}
