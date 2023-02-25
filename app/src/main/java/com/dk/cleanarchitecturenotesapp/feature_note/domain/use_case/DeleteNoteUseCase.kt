package com.dk.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.dk.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.dk.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note = note)
    }

}