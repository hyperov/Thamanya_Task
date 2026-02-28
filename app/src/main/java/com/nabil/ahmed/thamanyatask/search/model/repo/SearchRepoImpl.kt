package com.nabil.ahmed.thamanyatask.search.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepoImpl @Inject constructor(
    private val api: SearchApis
) : SearchRepo {

    override fun getSearchSections(): Flow<SectionsRes> = flow {
        emit(api.getSearchSections())
    }.flowOn(Dispatchers.IO)

}
