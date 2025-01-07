package com.example.tableofannouncements.data.modules

import com.example.tableofannouncements.data.repository.AnnouncementListRepositoryImpl
import com.example.tableofannouncements.domain.repository.AnnouncementListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseModule {
    @Binds
    abstract fun bindAnnouncementRepository(impl: AnnouncementListRepositoryImpl): AnnouncementListRepository
}