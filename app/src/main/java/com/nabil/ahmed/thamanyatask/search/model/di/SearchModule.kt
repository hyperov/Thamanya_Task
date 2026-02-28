package com.nabil.ahmed.thamanyatask.search.model.di

import com.nabil.ahmed.thamanyatask.home.model.repo.HomeApis
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepo
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepoImpl
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchApis
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepo
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Binds
    abstract fun bindSearchRepo(impl: SearchRepoImpl): SearchRepo

    companion object {
        @Provides
        @Singleton
        @Named("search")
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(SearchApis.Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideHomeApis(@Named("search") retrofit: Retrofit): SearchApis =
            retrofit.create(SearchApis::class.java)
    }
}