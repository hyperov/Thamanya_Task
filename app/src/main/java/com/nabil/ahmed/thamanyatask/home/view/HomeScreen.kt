package com.nabil.ahmed.thamanyatask.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nabil.ahmed.thamanyatask.home.viewmodel.HomePaginationViewModel
import com.nabil.ahmed.thamanyatask.home.viewmodel.HomeViewModel
import com.nabil.ahmed.thamanyatask.ui.components.SquareComponent
import com.nabil.ahmed.thamanyatask.utils.SectionViewType
import kotlin.random.Random

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    paginationViewModel: HomePaginationViewModel = viewModel()
) {

    val sections = paginationViewModel.sectionsPaging.collectAsLazyPagingItems()

    LazyColumn() {
        items(
            sections.itemCount,
            key = sections.itemKey { Random.nextInt() }
        ) { index ->
            val section = sections[index]
            if (section != null) {
                Column() {
                    Text(text = section.name)
                    Spacer(modifier = Modifier.height(8.dp))
                    when (section.type) {
                        SectionViewType.SQUARE.type ->
                            LazyRow(modifier = Modifier.height(100.dp) ) {
                                items(section.content, itemContent = { content ->
                                    SquareComponent(content, onClick = {})
                                })

                            }
                    }
                }
            } else {
                ProgressPlaceholder()
            }
        }
    }
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