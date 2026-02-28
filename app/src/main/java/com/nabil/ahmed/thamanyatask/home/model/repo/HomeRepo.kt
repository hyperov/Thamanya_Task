package com.nabil.ahmed.thamanyatask.home.model.repo

import androidx.paging.PagingData
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    fun getHomeSections(): Flow<SectionsRes>

    fun getSectionsPaging(): Flow<PagingData<Section>>
}
