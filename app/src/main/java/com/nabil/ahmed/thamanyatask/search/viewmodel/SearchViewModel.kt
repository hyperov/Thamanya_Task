package com.nabil.ahmed.thamanyatask.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.search.model.mapper.toSection
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepo
import com.nabil.ahmed.thamanyatask.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchSections = MutableStateFlow<ApiState<List<Section>>>(ApiState.Empty)
    val searchSections: StateFlow<ApiState<List<Section>>> = _searchSections.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _query
                .debounce(200)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collect { queryText ->
                    getSearchSections(queryText)
                }
        }
    }

    fun updateQuery(query: String) {
        _query.value = query
        if (query.isBlank()) {
            _searchSections.value = ApiState.Empty
        }
    }

    private fun getSearchSections(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchSections.value = ApiState.Loading
            searchRepo.getSearchSections()
                .catch { e -> _searchSections.value = ApiState.Error(e.message ?: "Unknown error") }
                .collect { data ->
                    _searchSections.value = ApiState.Success(
                        data.sections.map { it.toSection() }
                    )
                }
        }
    }
}