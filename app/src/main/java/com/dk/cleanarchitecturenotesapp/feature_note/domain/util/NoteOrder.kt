package com.dk.cleanarchitecturenotesapp.feature_note.domain.util

import com.dk.cleanarchitecturenotesapp.feature_note.presentation.notes.NotesEvent

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Colour(orderType: OrderType): NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder{
        return when(this){
            is Colour -> Colour(orderType = orderType)
            is Date -> Date(orderType = orderType)
            is Title -> Title(orderType = orderType)
        }
    }

}