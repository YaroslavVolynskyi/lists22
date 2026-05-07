package com.fin.list22.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fin.list22.data.NotesDao
import com.fin.list22.data.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(context = context, NotesDatabase::class.java, "notes.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun providesNotesDao(notesDb: NotesDatabase): NotesDao {
        return notesDb.notesDao()
    }
}