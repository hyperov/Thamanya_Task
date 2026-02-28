package com.nabil.ahmed.thamanyatask.search.model.mapper

import com.nabil.ahmed.thamanyatask.home.model.res.Content as HomeContent
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.search.model.res.Content as SearchContent
import com.nabil.ahmed.thamanyatask.search.model.res.SearchSection


fun SearchSection.toSection(): Section = Section(
    content = content.map { it.toHomeContent() },
    contentType = contentType,
    name = name,
    order = order.toIntOrNull() ?: 0,
    type = type
)

/**
 * Maps search API content DTO to home content model.
 */
fun SearchContent.toHomeContent(): HomeContent = HomeContent(
    avatarUrl = avatarUrl,
    description = description,
    duration = duration.toIntOrNull() ?: 0,
    name = name,
    score = score.toDoubleOrNull() ?: 0.0,
    episodeCount = episodeCount.toIntOrNull(),
    language = language.takeIf { it.isNotBlank() },
    podcastId = podcastId.takeIf { it.isNotBlank() },
    popularityScore = popularityScore.toIntOrNull(),
    priority = priority.toIntOrNull()
)
