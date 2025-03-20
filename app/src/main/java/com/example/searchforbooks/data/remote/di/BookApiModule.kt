package com.example.searchforbooks.data.remote.di

import com.example.searchforbooks.data.remote.BookApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BookApiModule {
    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit) = retrofit.create(BookApi::class.java)
}