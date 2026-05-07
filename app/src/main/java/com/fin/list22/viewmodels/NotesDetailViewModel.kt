package com.fin.list22.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fin.list22.data.NoteEntry
import com.fin.list22.repository.NotesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = NotesDetailViewModel.Factory::class)
class NotesDetailViewModel @AssistedInject constructor(
    val repository: NotesRepository,
    @Assisted val noteId: Long
): ViewModel() {

    val uiState: StateFlow<NoteEntry?> = repository
        .getById(noteId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @AssistedFactory
    interface Factory {
        fun create(noteId: Long): NotesDetailViewModel
    }
}