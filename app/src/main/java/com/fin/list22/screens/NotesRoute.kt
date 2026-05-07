package com.fin.list22.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fin.list22.UiState
import com.fin.list22.data.NoteEntry
import com.fin.list22.viewmodels.NotesViewModel

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit,
) {
    val uiState = notesViewModel.uiState.collectAsStateWithLifecycle()

    NotesScreen(
        uiState = uiState.value,
        navigateToDetail = navigateToDetail,
        onAddNote = notesViewModel::addNote,
        onStartEdit = notesViewModel::onStartEdit,
        onEditTextChange = notesViewModel::onEditTextChange,
        onSaveEdit = notesViewModel::onSaveEdit,
        onCancelEdit = notesViewModel::onCancelEdit,
        onDelete = notesViewModel::deleteNote,
        modifier = modifier
    )
}

@Composable
fun NotesScreen(
    uiState: UiState,
    navigateToDetail: (Long) -> Unit,
    onAddNote: (String) -> Unit,
    onStartEdit: (NoteEntry) -> Unit,
    onEditTextChange: (String) -> Unit,
    onSaveEdit: () -> Unit,
    onCancelEdit: () -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddNote("note ${uiState.notes.size + 1}")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(uiState.notes, key = { it.id }) { note ->
                val isEditing = uiState.currentNote?.id == note.id

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditing) {
                        TextField(
                            value = uiState.currentNote?.currentText.orEmpty(),
                            onValueChange = onEditTextChange,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = onSaveEdit) {
                            Icon(Icons.Default.Check, contentDescription = "Save")
                        }
                        IconButton(onClick = onCancelEdit) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                    } else {
                        TextField(
                            value = note.text.orEmpty(),
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { navigateToDetail(note.id) }
                        )
                        IconButton(onClick = { onStartEdit(note) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDelete(note.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}