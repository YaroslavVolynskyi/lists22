package com.fin.list22.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fin.list22.CurrentEditedNote
import com.fin.list22.UiState
import com.fin.list22.data.NoteEntry
import com.fin.list22.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    val repository: NotesRepository
): ViewModel() {

    val _currentNote = MutableStateFlow(CurrentEditedNote())

    val uiState: StateFlow<UiState> = combine(
        repository.getNotes(),
        _currentNote
    ) { notes, currentNote ->
        UiState(notes, currentNote)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun addNote(text: String) {
        viewModelScope.launch {
            repository.addNote(
                NoteEntry(
                    text = text
                )
            )
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun onStartEdit(item: NoteEntry) {
        _currentNote.value = _currentNote.value.copy(
            id = item.id,
            currentText = item.text
        )
    }

    fun onEditTextChange(text: String) {
        _currentNote.value = _currentNote.value.copy(
            currentText = text
        )
    }

    fun onSaveEdit() {
        _currentNote.value.let {
            if (it.currentText != null && it.id != null) {
                viewModelScope.launch {
                    repository.updateNote(it.id, it.currentText)
                }
            }
        }
        _currentNote.value = CurrentEditedNote()
    }

    fun onCancelEdit() {
        _currentNote.value = CurrentEditedNote()
    }
}
