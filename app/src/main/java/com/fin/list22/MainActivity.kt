package com.fin.list22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.fin.list22.navigation.NoteDetailKey
import com.fin.list22.navigation.NotesKey
import com.fin.list22.screens.NoteDetailRoute
import com.fin.list22.screens.NotesRoute
import com.fin.list22.ui.theme.List22Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            List22Theme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { _ ->
                    val backStack = rememberNavBackStack(
                        configuration = SavedStateConfiguration {
                            serializersModule = SerializersModule {
                                polymorphic(NavKey::class) {
                                    subclass(NotesKey::class, NotesKey.serializer())
                                }
                                polymorphic(NavKey::class) {
                                    subclass(NoteDetailKey::class, NoteDetailKey.serializer())
                                }
                            }
                        },
                        NotesKey
                    )

                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryDecorators = listOf(

//                            rememberSavedStateNavEntryDecorator(),       // sets up SavedStateRegistry per entry
//                            rememberViewModelStoreNavEntryDecorator(),
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            entry<NotesKey> {
                                NotesRoute(
                                    navigateToDetail = { id -> backStack.add(NoteDetailKey(id)) }
                                )
                            }
                            entry<NoteDetailKey> { key ->
                                NoteDetailRoute(noteId = key.noteId)
                            }
                        }
                    )
                }
            }
        }
    }
}
