package com.dk.cleanarchitecturenotesapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addnote: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
)