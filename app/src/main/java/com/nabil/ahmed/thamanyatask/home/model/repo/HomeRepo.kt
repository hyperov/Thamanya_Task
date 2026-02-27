package com.nabil.ahmed.thamanyatask.home.model.repo

import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import com.nabil.ahmed.thamanyatask.utils.ApiState
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    fun getHomeSections(): Flow<SectionsRes>
}
