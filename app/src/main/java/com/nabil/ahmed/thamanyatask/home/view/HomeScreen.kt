package com.nabil.ahmed.thamanyatask.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.nabil.ahmed.thamanyatask.home.viewmodel.HomePaginationViewModel
import com.nabil.ahmed.thamanyatask.ui.components.SectionsScreen

@Composable
fun HomeScreen(
    paginationViewModel: HomePaginationViewModel = viewModel(),
    modifier: Modifier
) {

    val sections = paginationViewModel.sectionsPaging.collectAsLazyPagingItems()

    SectionsScreen(lazySections = sections, modifier = modifier, isSearchScreen = false)

}

@Composable
fun ProgressPlaceholder() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        CircularProgressIndicator()
    }
}