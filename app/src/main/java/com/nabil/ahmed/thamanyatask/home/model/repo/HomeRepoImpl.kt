package com.nabil.ahmed.thamanyatask.home.model.repo

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
class HomeRepoImpl @Inject constructor(
    private val api: HomeApis
) : HomeRepo {

    override fun getHomeSections(): Flow<SectionsRes> = flow {
        emit(api.getHomeSections())
    }.flowOn(Dispatchers.IO)

    override fun getSectionsPaging(): Flow<PagingData<Section>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SectionsPagingSource(api) }
    ).flow
}
