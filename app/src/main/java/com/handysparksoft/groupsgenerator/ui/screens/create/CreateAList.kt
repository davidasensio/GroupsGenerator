package com.handysparksoft.groupsgenerator.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp

@Composable
fun CreateAList() {
    val (name, setName) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Type the list name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = setDescription,
                label = { Text(text = "Description") },
                placeholder = { Text(text = "Type a description for this list") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("Create")
        }
    }
}

@Preview
@Composable
fun CreateAListPreview() {
    GroupsGeneratorApp {
        CreateAList()
    }
}
