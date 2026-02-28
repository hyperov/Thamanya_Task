package com.nabil.ahmed.thamanyatask.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.nabil.ahmed.thamanyatask.home.viewmodel.HomePaginationViewModel
import com.nabil.ahmed.thamanyatask.home.viewmodel.HomeViewModel
import com.nabil.ahmed.thamanyatask.ui.components.BigSquareComponent
import com.nabil.ahmed.thamanyatask.ui.components.SquareComponent
import com.nabil.ahmed.thamanyatask.ui.components.SquareWithTitlesAndSubtitles
import com.nabil.ahmed.thamanyatask.utils.SectionViewType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    paginationViewModel: HomePaginationViewModel = viewModel()
) {

    val sections = paginationViewModel.sectionsPaging.collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            sections.itemCount,
        ) { index ->
            val section = sections[index]
            if (section != null) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                    Text(text = section.name)
                    Spacer(modifier = Modifier.height(8.dp))

                    when (section.type) {
                        SectionViewType.SQUARE.type ->
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(section.content.sortedBy { it.priority }) { content ->
                                    SquareComponent(
                                        content,
                                        modifier = Modifier.size(140.dp),
                                        onClick = {}
                                    )
                                }
                            }

                        SectionViewType.TWO_LINES_GRID.type ->
                            LazyHorizontalGrid(
                                modifier = Modifier.height(140.dp),
                                rows = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            )
                            {
                                items(section.content.sortedBy { it.podcastPriority }) { content ->
                                    SquareWithTitlesAndSubtitles(
                                        imageUrl = content.avatarUrl,
                                        title = content.name,
                                        subtitle = content.description,
                                        duration = content.duration,
                                        onClick = {})
                                }
                            }

                        SectionViewType.BIG_SQUARE.type ->
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(section.content) { content ->
                                    BigSquareComponent(
                                        content,
                                        modifier = Modifier.size(height = 140.dp, width = 240.dp),
                                        onClick = {}
                                    )
                                }
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