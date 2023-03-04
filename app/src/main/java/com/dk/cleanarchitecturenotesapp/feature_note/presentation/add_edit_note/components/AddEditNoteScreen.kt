package com.dk.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.components

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dk.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.dk.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.dk.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    noteColour: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()

    val noteBackgroundAnimatable = remember{
        Animatable(
            Color(if(noteColour != -1) noteColour else viewModel.noteColour.value)
        )
    }
    
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState =  scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColours.forEach{ colour->
                    val colourInt = colour.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(colour)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColour.value == colourInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colourInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }

                                viewModel.onEvent(AddEditNoteEvent.ChangeColour(colourInt))
                            }
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {title ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
                },
                onFocusChange = {focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(focusState))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )


            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {content ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(content))
                },
                onFocusChange = {focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(focusState))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
    }

}