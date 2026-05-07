package com.fin.list22.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fin.list22.data.NoteEntry
import com.fin.list22.viewmodels.NotesDetailViewModel

@Composable
fun NoteDetailRoute(
    noteId: Long,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<NotesDetailViewModel, NotesDetailViewModel.Factory>(
        creationCallback = { factory: NotesDetailViewModel.Factory -> factory.create(noteId) }
    )
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    uiState.value?.let { note ->
        NoteDetailScreen(note = note, modifier = modifier)
    }
}

@Composable
fun NoteDetailScreen(
    note: NoteEntry,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = note.text.orEmpty(),
            style = MaterialTheme.typography.headlineMedium
        )
        TextField(
            value = note.details.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Details") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
    }
}