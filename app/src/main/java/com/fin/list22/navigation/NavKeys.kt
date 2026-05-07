package com.fin.list22.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object NotesKey: NavKey {
}

@Serializable
data class NoteDetailKey(
    val noteId: Long
): NavKey {

}