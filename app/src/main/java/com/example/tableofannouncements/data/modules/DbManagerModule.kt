package com.example.tableofannouncements.data.modules

import com.example.tableofannouncements.data.database.DbManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbManagerModule {
    @Provides
    @Singleton
    fun provideDbManager(): DbManager {
        return DbManager()
    }
}