package com.nabil.ahmed.thamanyatask.search.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import com.nabil.ahmed.thamanyatask.search.model.res.SearchRes
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApis {

    @GET("search")
    suspend fun getSearchSections(): SearchRes

    companion object {
        const val BASE_URL = "https://mock.apidog.com/m1/735111-711675-default/"

    }

}