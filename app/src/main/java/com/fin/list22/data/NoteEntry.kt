package com.fin.list22.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String? = null,
    val details: String? = null
)
