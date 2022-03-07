package com.example.mylibrary.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Named("temp")
    @Singleton
    @Provides
    fun provideFirebaseDB(): DatabaseReference = Firebase.database.reference

    @Named("UserDB")
    @Singleton
    @Provides
    fun provideFirebaseUserDB(): DatabaseReference = Firebase.database.reference.child("User")

    @Named("BookDB")
    @Singleton
    @Provides
    fun provideFirebaseBookDB(): DatabaseReference = Firebase.database.reference.child("Book")
}