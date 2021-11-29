package com.handysparksoft.groupsgenerator.ui.screens.generate

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.handysparksoft.groupsgenerator.R
import com.handysparksoft.groupsgenerator.model.Participant
import com.handysparksoft.groupsgenerator.platform.ShareIntentHandler
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.shared.DropdownField
import com.handysparksoft.groupsgenerator.ui.theme.BackgroundSecondary
import com.handysparksoft.groupsgenerator.ui.theme.BackgroundSecondaryDark
import com.handysparksoft.groupsgenerator.ui.theme.GroupHeader
import com.handysparksoft.groupsgenerator.ui.theme.GroupHeaderDark
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenerateScreen(
    viewModel: GenerateViewModel,
    onUpClick: () -> Unit
) {
    val context = LocalContext.current
    val options = listOf(
        stringResource(R.string.generate_mode_number_of_groups),
        stringResource(R.string.generate_mode_participants_per_group)
    )
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (selectedOptionText, setSelectedOptionText) = remember { mutableStateOf(options[0]) }
    val listParticipantsCount = viewModel.aList.itemActiveCount
    val defaultSliderValue = 2f / listParticipantsCount
    val (sliderValue, setSliderValue) = remember { mutableStateOf(defaultSliderValue) }
    val elementsNumber = (sliderValue * listParticipantsCount).toInt()

    val onGenerateRandomGroups = {
        viewModel.generateRandomGroups(
            mode = options.indexOf(selectedOptionText),
            elementsNumber = elementsNumber
        )
    }

    val listState = rememberLazyListState(0)
    val optionsSectionHeight = 280.dp
    val optionsSectionHeightPx =
        with(LocalDensity.current) { optionsSectionHeight.roundToPx().toFloat() }
    val optionsSectionOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (listState.firstVisibleItemScrollOffset > 0) {
                    val delta = available.y
                    val newOffset = optionsSectionOffsetHeightPx.value + delta
                    optionsSectionOffsetHeightPx.value =
                        newOffset.coerceIn(-optionsSectionHeightPx, 0f)
                }
                return Offset.Zero
            }
        }
    }

    GenerateScreenScaffold(
        onUpClick = onUpClick,
        onCopyGroupsClick = {
            val copiedToast = Resources.getSystem().getString(R.string.generate_copied_to_clipboard)

            if (viewModel.generatedGroups.isNotEmpty()) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("generated_groups", viewModel.getContent())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, copiedToast, Toast.LENGTH_SHORT).show()
            }
        },
        onShareGeneratedGroupsClick = {
            if (viewModel.generatedGroups.isNotEmpty()) {
                onShareGeneratedGroups(context, viewModel.getContent())
            }
        }
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)

        ) {
            ListOfGeneratedGroups(listState, viewModel, optionsSectionHeight)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Top)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = optionsSectionOffsetHeightPx.value.roundToInt()
                        )
                    }
                    .background(
                        color = when (isSystemInDarkTheme()) {
                            true -> BackgroundSecondaryDark
                            false -> BackgroundSecondary
                        }
                    )
                    .padding(16.dp)
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
                        label = stringResource(R.string.generate_mode_label),
                        modifier = Modifier.padding(vertical = 12.dp)
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
                    steps = max(listParticipantsCount - 1, 0)
                )

                Button(
                    onClick = onGenerateRandomGroups,
                    enabled = elementsNumber > 0,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 12.dp)
                        .height(48.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Casino, contentDescription = null)
                        Text(text = stringResource(R.string.generate_random_generation))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListOfGeneratedGroups(
    listState: LazyListState,
    viewModel: GenerateViewModel,
    optionsSectionHeight: Dp,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentPadding = PaddingValues(bottom = 60.dp, top = optionsSectionHeight)
    ) {
        itemsIndexed(viewModel.generatedGroups) { index, group ->
            GroupCard(index + 1, group, modifier = Modifier.padding(4.dp))
        }
    }
    /*LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentPadding = PaddingValues(bottom = 60.dp, top = optionsSectionHeight)
    ) {
        itemsIndexed(viewModel.generatedGroups) { index, group ->
            GroupCard(index + 1, group)
        }
    }*/
}

@Composable
fun GroupCard(index: Int, participants: List<Participant>, modifier: Modifier = Modifier) {
    SelectionContainer() {
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = 0.dp,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column() {
                Text(
                    text = stringResource(id = R.string.generate_group_title, index),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isSystemInDarkTheme()) GroupHeaderDark else GroupHeader
                        )
                        .padding(8.dp)
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    participants.forEach {
                        Text(
                            text = it.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
