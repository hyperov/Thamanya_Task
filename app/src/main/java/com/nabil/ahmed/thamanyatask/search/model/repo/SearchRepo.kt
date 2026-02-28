package com.nabil.ahmed.thamanyatask.search.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import com.nabil.ahmed.thamanyatask.search.model.res.SearchRes
import kotlinx.coroutines.flow.Flow

interface SearchRepo {
    fun getSearchSections(): Flow<SearchRes>

}
