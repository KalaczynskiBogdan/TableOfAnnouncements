package com.example.tableofannouncements.data.modules

import com.example.tableofannouncements.data.repository.AddNewAnnRepositoryImpl
import com.example.tableofannouncements.domain.repository.AddNewAnnRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAddNewAnnRepository(impl: AddNewAnnRepositoryImpl): AddNewAnnRepository
}