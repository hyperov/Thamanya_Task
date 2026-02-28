package com.nabil.ahmed.thamanyatask.home.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApis {

    @GET("home_sections")
    suspend fun getHomeSections(@Query("page") page: Int): SectionsRes

    companion object {
        const val BASE_URL = "https://api-v2-b2sit6oh3a-uc.a.run.app/"

    }

}