package com.nabil.ahmed.thamanyatask.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nabil.ahmed.thamanyatask.search.viewmodel.SearchViewModel
import com.nabil.ahmed.thamanyatask.ui.components.SectionsScreen
import com.nabil.ahmed.thamanyatask.utils.ApiState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val searchState by viewModel.searchSections.collectAsStateWithLifecycle()

    when (val state = searchState) {
        is ApiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ApiState.Success -> {
            SectionsScreen(
                isSearchScreen = true,
                searchSections = state.data,
                modifier = modifier
            )
        }
        is ApiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        is ApiState.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "اكتب للبحث",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
