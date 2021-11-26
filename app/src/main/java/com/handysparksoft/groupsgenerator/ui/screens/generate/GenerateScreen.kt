package com.handysparksoft.groupsgenerator.ui.screens.generate

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.model.Participant
import com.handysparksoft.groupsgenerator.platform.ShareIntentHandler
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.shared.DropdownField
import kotlin.math.max

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenerateScreen(
    viewModel: GenerateViewModel,
    onUpClick: () -> Unit
) {
    val context = LocalContext.current
    val options = listOf(
        "Number of groups",
        "Number of elements per group"
    )
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (selectedOptionText, setSelectedOptionText) = remember { mutableStateOf(options[0]) }
    val listElementsCount = viewModel.aList.itemActiveCount
    val defaultSliderValue = 2f / listElementsCount
    val (sliderValue, setSliderValue) = remember { mutableStateOf(defaultSliderValue) }
    val elementsNumber = (sliderValue * listElementsCount).toInt()

    val onGenerateRandomGroups = {
        viewModel.generateRandomGroups(
            mode = options.indexOf(selectedOptionText),
            elementsNumber = elementsNumber
        )
    }

    GenerateScreenScaffold(
        onUpClick = onUpClick,
        onCopyGroupsClick = {
            if (viewModel.generatedGroups.isNotEmpty()) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("generated_groups", viewModel.getContent())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Groups copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        },
        onShareGeneratedGroupsClick = {
            if (viewModel.generatedGroups.isNotEmpty()) {
                onShareGeneratedGroups(context, viewModel.getContent())
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${viewModel.listName} (${viewModel.aList.itemRealCount})",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                DropdownField(
                    expanded = expanded,
                    onExpandedChange = setExpanded,
                    selectedOptionText = selectedOptionText,
                    onSelectedOptionTextChange = setSelectedOptionText,
                    options = options,
                    label = "Generation mode",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    text = elementsNumber.toString(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.weight(1f)
                )
            }

            Slider(
                value = sliderValue, onValueChange = setSliderValue,
                steps = max(listElementsCount - 1, 0)
            )

            Button(
                onClick = onGenerateRandomGroups,
                enabled = elementsNumber > 0,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 8.dp)
                    .height(48.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Casino, contentDescription = null)
                    Text(text = "Random generation")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                itemsIndexed(viewModel.generatedGroups) { index, group ->
                    GroupCard(index + 1, group)
                }
            }
        }
    }
}

@Composable
fun GroupCard(index: Int, group: List<Participant>) {
    SelectionContainer() {
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = 0.dp,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "- Group $index -",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                group.forEach {
                    Text(text = it.name)
                }
            }
        }
    }
}

fun onShareGeneratedGroups(context: Context, content: String) {
    (context as? Activity)?.let {
        ShareIntentHandler.showShareGroupsIntentChooser(
            activity = it,
            content = content
        )
    }
}

@Preview
@Composable
fun GenerateAListPreview() {
    GroupsGeneratorApp {
        GenerateScreen(viewModel = GenerateViewModel(), onUpClick = {})
    }
}
