package com.fin.list22

import com.fin.list22.data.NoteEntry

data class UiState(
    val notes: List<NoteEntry> = emptyList(),
    val currentNote: CurrentEditedNote? = null
)
