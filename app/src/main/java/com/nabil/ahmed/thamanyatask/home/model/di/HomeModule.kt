package com.nabil.ahmed.thamanyatask.home.model.di

import com.nabil.ahmed.thamanyatask.home.model.repo.HomeApis
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepo
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    abstract fun bindHomeRepo(impl: HomeRepoImpl): HomeRepo

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(HomeApis.Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideHomeApis(retrofit: Retrofit): HomeApis =
            retrofit.create(HomeApis::class.java)
    }
}