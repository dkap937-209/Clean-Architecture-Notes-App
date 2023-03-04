package com.dk.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.components

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColour(val colour: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
