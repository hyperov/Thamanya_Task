package com.nabil.ahmed.thamanyatask.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepo
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo
) : ViewModel() {

    private val _homeSections = MutableStateFlow<ApiState<List<Section>>>(ApiState.Empty)
    val homeSections: StateFlow<ApiState<List<Section>>> = _homeSections.asStateFlow()

    init {
        getHomeSections()
    }

    fun getHomeSections() {
        viewModelScope.launch {
            _homeSections.value = ApiState.Loading
            homeRepo.getHomeSections()
                .catch { e -> _homeSections.value = ApiState.Error(e.message ?: "Unknown error") }
                .collect { data -> _homeSections.value = ApiState.Success(data.sections) }
        }
    }

}
