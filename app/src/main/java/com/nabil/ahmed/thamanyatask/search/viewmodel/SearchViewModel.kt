package com.nabil.ahmed.thamanyatask.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepo
import com.nabil.ahmed.thamanyatask.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
) : ViewModel() {

    private val _searchSections = MutableStateFlow<ApiState<List<Section>>>(ApiState.Empty)
    val searchSections: StateFlow<ApiState<List<Section>>> = _searchSections.asStateFlow()


    fun getSearchSections(searchText: String) {
        viewModelScope.launch {
            _searchSections.value = ApiState.Loading
            searchRepo.getSearchSections()
                .catch { e -> _searchSections.value = ApiState.Error(e.message ?: "Unknown error") }
                .collect { data -> _searchSections.value = ApiState.Success(data.sections) }
        }
    }

}