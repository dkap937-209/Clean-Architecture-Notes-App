package com.dk.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.dk.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.dk.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import com.dk.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.dk.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType){
                OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Colour -> notes.sortedBy { it.colour }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
                OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Colour -> notes.sortedByDescending { it.colour }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }

}