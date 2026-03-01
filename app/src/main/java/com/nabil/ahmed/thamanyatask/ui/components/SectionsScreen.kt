package com.nabil.ahmed.thamanyatask.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.view.ProgressPlaceholder
import com.nabil.ahmed.thamanyatask.utils.SectionViewType

@Composable
fun SectionsScreen(
    isSearchScreen: Boolean,
    lazySections: LazyPagingItems<Section>? = null,
    searchSections: List<Section>? = null,
    modifier: Modifier
) {


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            if (!isSearchScreen) lazySections!!.itemCount else searchSections!!.size,
        ) { index ->
            val section = if (!isSearchScreen) lazySections!![index] else searchSections!![index]
            if (section != null) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                    Text(text = section.name, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))

                    when (section.type) {
                        SectionViewType.SQUARE.type -> {
                            val sortedContent = section.content.sortedBy { it.priority }
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(sortedContent.size) { index ->
                                    val content = sortedContent[index]
                                    SquareComponent(
                                        content,
                                        modifier = Modifier.size(140.dp),
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        SectionViewType.TWO_LINES_GRID.type -> {
                            val sortedContent = section.content.sortedBy { it.podcastPriority }
                            LazyHorizontalGrid(
                                modifier = Modifier.height(140.dp),
                                rows = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                items(sortedContent.size) { index ->
                                    val content = sortedContent[index]
                                    SquareWithTitlesAndSubtitles(
                                        imageUrl = content.avatarUrl,
                                        title = content.name,
                                        subtitle = content.podcastName ?: "",
                                        duration = content.duration,
                                        authorName = content.authorName ?: "",
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        SectionViewType.BIG_SQUARE.type ->
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(section.content.size) { index ->
                                    val content = section.content[index]
                                    BigSquareComponent(
                                        content,
                                        modifier = Modifier.size(height = 140.dp, width = 240.dp),
                                        onClick = {}
                                    )
                                }
                            }

                        SectionViewType.QUEUE.type ->
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(section.content.size) { index ->
                                    val content = section.content[index]
                                    QueueComponent(
                                        content,
                                        modifier = Modifier.size(height = 240.dp, width = 300.dp),
                                        onClick = {}
                                    )
                                }
                            }

                    }
                    if(isSearchScreen) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp)
                        ) {
                            items(section.content.size) { index ->
                                val content = section.content[index]
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