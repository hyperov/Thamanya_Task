package com.nabil.ahmed.thamanyatask.search.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import kotlinx.coroutines.flow.Flow

interface SearchRepo {
    fun getSearchSections(): Flow<SectionsRes>

}
