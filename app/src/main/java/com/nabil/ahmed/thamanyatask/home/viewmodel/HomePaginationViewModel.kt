package com.nabil.ahmed.thamanyatask.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepo
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomePaginationViewModel @Inject constructor(
    private val homeRepo: HomeRepo
) : ViewModel() {

    val sectionsPaging: Flow<PagingData<Section>>
        get() = homeRepo
        .getSectionsPaging()
        .cachedIn(viewModelScope)
}
