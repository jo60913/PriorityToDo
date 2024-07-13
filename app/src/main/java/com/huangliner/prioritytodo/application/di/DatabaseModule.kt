package com.huangliner.prioritytodo.application.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huangliner.prioritytodo.data.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providerDatabase(
        @ApplicationContext context: Context
    ) =  Room.databaseBuilder(context, TodoDatabase::class.java,"TodoDatabase")
        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
        .setQueryCallback({ sqlQuery, bindArgs ->
            Timber.e("資料庫執行 SQL : $sqlQuery, Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
        .build()

    @Singleton
    @Provides
    fun providerTodoItemDao(
        database: TodoDatabase
    ) = database.getTodoItemDao()
}