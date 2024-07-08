package com.huangliner.prioritytodo.application.di

import com.huangliner.prioritytodo.data.repository.RepositoryImpl
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsRepository(implement : RepositoryImpl) : IRepositry
}