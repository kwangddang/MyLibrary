package com.example.mylibrary.di

import com.example.mylibrary.data.repository.*
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

    @Binds
    abstract fun provideBookRepository(
        repository: BookRepositoryImpl
    ): BookRepository

    @Binds
    abstract fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun provideFirebaseRepository(
        repository: FirebaseRepositoryImpl
    ): FirebaseRepository
}