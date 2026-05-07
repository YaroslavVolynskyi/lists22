package com.fin.list22.di

import com.fin.list22.repository.NotesRepository
import com.fin.list22.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepositoty(notesRepository: NotesRepositoryImpl): NotesRepository
}