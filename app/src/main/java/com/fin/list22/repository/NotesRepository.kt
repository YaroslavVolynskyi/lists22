package com.fin.list22.repository

import com.fin.list22.data.NoteEntry
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<NoteEntry>>

    suspend fun addNote(note: NoteEntry): Long

    suspend fun updateNote(id: Long, text: String): Int

    fun getById(noteId: Long): Flow<NoteEntry?>

    suspend fun deleteNote(noteId: Long): Int
}