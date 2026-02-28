package com.nabil.ahmed.thamanyatask.search.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApis {

    @GET("search")
    suspend fun getSearchSections(): SectionsRes

    companion object {
        const val BASE_URL = "https://mock.apidog.com/m1/735111-711675-default/"

    }

}