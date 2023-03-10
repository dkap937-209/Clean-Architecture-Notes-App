package com.dk.cleanarchitecturenotesapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dk.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDatabase
import com.dk.cleanarchitecturenotesapp.feature_note.data.repository.NoteRepositoryImpl
import com.dk.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import com.dk.cleanarchitecturenotesapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        app: Application
    ): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        NoteDatabase.DATABASE_NAME
    ).addMigrations(object: Migration(1, 2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE IF EXISTS Note")
            database.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, colour INTEGER NOT NULL DEFAULT 0, timestamp INTEGER NOT NULL DEFAULT 0)")
        }
    }).
    build()

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDatabase
    ): NoteRepository = NoteRepositoryImpl(db.noteDao)

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases = NoteUseCases(
        getNotesUseCase = GetNotesUseCase(repository),
        deleteNoteUseCase = DeleteNoteUseCase(repository),
        addNoteUseCase = AddNoteUseCase(repository),
        getNoteUseCase = GetNoteUseCase(repository)
    )
}