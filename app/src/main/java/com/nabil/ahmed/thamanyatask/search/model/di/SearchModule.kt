package com.nabil.ahmed.thamanyatask.search.model.di

import com.nabil.ahmed.thamanyatask.search.model.repo.SearchApis
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepo
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Binds
    abstract fun bindSearchRepo(impl: SearchRepoImpl): SearchRepo

    companion object {

        @Provides
        @Singleton
        fun provideHomeApis(retrofit: Retrofit): SearchApis =
            retrofit.create(SearchApis::class.java)
    }
}