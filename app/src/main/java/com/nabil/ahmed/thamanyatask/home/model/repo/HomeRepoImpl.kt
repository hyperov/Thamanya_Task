package com.nabil.ahmed.thamanyatask.home.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import com.nabil.ahmed.thamanyatask.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepoImpl @Inject constructor(
    private val api: HomeApis
) : HomeRepo {
    override fun getHomeSections(): Flow<SectionsRes> = flow {
        emit(api.getHomeSections())
    }.flowOn(Dispatchers.IO)
}
