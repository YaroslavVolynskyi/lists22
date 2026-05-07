package com.fin.list22.repository

import com.fin.list22.data.NoteEntry
import com.fin.list22.data.NotesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    val notesDao: NotesDao
): NotesRepository {

    override fun getNotes(): Flow<List<NoteEntry>> {
        return notesDao.observeNotes()
    }

    override suspend fun addNote(note: NoteEntry): Long {
        return notesDao.upsertNote(note)
    }

    override suspend fun updateNote(id: Long, text: String): Int {
        return notesDao.updateNote(id, text)
    }

    override fun getById(noteId: Long): Flow<NoteEntry?> {
        return notesDao.getById(noteId)
    }

    override suspend fun deleteNote(noteId: Long): Int {
        return notesDao.deleteNote(noteId)
    }
}