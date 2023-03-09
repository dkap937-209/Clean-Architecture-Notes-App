package com.dk.cleanarchitecturenotesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dk.cleanarchitecturenotesapp.ui.theme.*
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColours = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)
