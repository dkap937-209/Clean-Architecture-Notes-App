package com.dk.cleanarchitecturenotesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dk.cleanarchitecturenotesapp.ui.theme.*
import java.sql.Timestamp

@Entity
data class Note(
    val title: String,
    val context: String,
    val timestamp: Long,
    val colour: Int,
    @PrimaryKey val id: Int? = null
){

    companion object{
        val noteColours = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

}
