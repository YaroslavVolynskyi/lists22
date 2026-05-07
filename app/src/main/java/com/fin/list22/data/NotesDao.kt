package com.fin.list22.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun observeNotes(): Flow<List<NoteEntry>>

    @Upsert
    suspend fun upsertNote(note: NoteEntry): Long

    @Query("UPDATE notes SET text = :text WHERE id = :id")
    suspend fun updateNote(id: Long, text: String): Int

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Long): Flow<NoteEntry?>

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Long): Int
}
