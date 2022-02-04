package com.example.mylibrary.di

import com.example.mylibrary.data.repository.NaverRepository
import com.example.mylibrary.data.repository.NaverRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideNaverRepository(
        repository: NaverRepositoryImpl
    ): NaverRepository
}