package com.nabil.ahmed.thamanyatask.search.model.mapper

import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.search.model.res.Content as SearchContent
import com.nabil.ahmed.thamanyatask.search.model.res.SearchSection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SearchSectionMapperTest {

    @Test
    fun searchSection_toSection_withValidOrder_mapsCorrectly() {
        val searchSection = SearchSection(
            content = listOf(
                SearchContent(
                    avatarUrl = "https://example.com/avatar.png",
                    description = "A podcast",
                    duration = "3600",
                    episodeCount = "12",
                    language = "en",
                    name = "Podcast Name",
                    podcastId = "pod-123",
                    popularityScore = "100",
                    priority = "1",
                    score = "4.5"
                )
            ),
            contentType = "podcast",
            name = "Section Name",
            order = "2",
            type = "square"
        )

        val result = searchSection.toSection()

        assertEquals("Section Name", result.name)
        assertEquals("podcast", result.contentType)
        assertEquals("square", result.type)
        assertEquals(2, result.order)
        assertEquals(1, result.content.size)
        assertEquals("Podcast Name", result.content[0].name)
        assertEquals("A podcast", result.content[0].description)
        assertEquals("https://example.com/avatar.png", result.content[0].avatarUrl)
        assertEquals(3600, result.content[0].duration)
        assertEquals(12, result.content[0].episodeCount)
        assertEquals("en", result.content[0].language)
        assertEquals("pod-123", result.content[0].podcastId)
        assertEquals(100, result.content[0].popularityScore)
        assertEquals(1, result.content[0].priority)
        assertEquals(4.5, result.content[0].score, 0.001)
    }

    @Test
    fun searchSection_toSection_withInvalidOrder_defaultsToZero() {
        val searchSection = SearchSection(
            content = emptyList(),
            contentType = "podcast",
            name = "Section",
            order = "not_a_number",
            type = "queue"
        )

        val result = searchSection.toSection()

        assertEquals(0, result.order)
    }

    @Test
    fun searchSection_toSection_withEmptyContent_mapsEmptyList() {
        val searchSection = SearchSection(
            content = emptyList(),
            contentType = "podcast",
            name = "Empty Section",
            order = "1",
            type = "square"
        )

        val result = searchSection.toSection()

        assertEquals(0, result.content.size)
    }

    @Test
    fun searchSection_toSection_withMultipleContent_mapsAllItems() {
        val searchSection = SearchSection(
            content = listOf(
                SearchContent(
                    avatarUrl = "url1",
                    description = "desc1",
                    duration = "100",
                    episodeCount = "5",
                    language = "ar",
                    name = "Item 1",
                    podcastId = "id1",
                    popularityScore = "10",
                    priority = "0",
                    score = "3.0"
                ),
                SearchContent(
                    avatarUrl = "url2",
                    description = "desc2",
                    duration = "200",
                    episodeCount = "20",
                    language = "en",
                    name = "Item 2",
                    podcastId = "id2",
                    popularityScore = "50",
                    priority = "2",
                    score = "5.0"
                )
            ),
            contentType = "podcast",
            name = "Multi",
            order = "3",
            type = "big_square"
        )

        val result = searchSection.toSection()

        assertEquals(2, result.content.size)
        assertEquals("Item 1", result.content[0].name)
        assertEquals(100, result.content[0].duration)
        assertEquals("Item 2", result.content[1].name)
        assertEquals(200, result.content[1].duration)
    }

    @Test
    fun searchContent_toHomeContent_withValidNumericStrings_mapsCorrectly() {
        val searchContent = SearchContent(
            avatarUrl = "https://avatar.url",
            description = "Description text",
            duration = "120",
            episodeCount = "8",
            language = "en",
            name = "Content Name",
            podcastId = "pod-456",
            popularityScore = "99",
            priority = "3",
            score = "4.75"
        )

        val result = searchContent.toHomeContent()

        assertEquals("https://avatar.url", result.avatarUrl)
        assertEquals("Description text", result.description)
        assertEquals(120, result.duration)
        assertEquals(8, result.episodeCount)
        assertEquals("en", result.language)
        assertEquals("Content Name", result.name)
        assertEquals("pod-456", result.podcastId)
        assertEquals(99, result.popularityScore)
        assertEquals(3, result.priority)
        assertEquals(4.75, result.score, 0.001)
    }

    @Test
    fun searchContent_toHomeContent_withNonNumericDuration_defaultsToZero() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "invalid",
            episodeCount = "5",
            language = "en",
            name = "Name",
            podcastId = "id",
            popularityScore = "10",
            priority = "1",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertEquals(0, result.duration)
    }

    @Test
    fun searchContent_toHomeContent_withNonNumericScore_defaultsToZero() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "5",
            language = "en",
            name = "Name",
            podcastId = "id",
            popularityScore = "10",
            priority = "1",
            score = "not_a_number"
        )

        val result = searchContent.toHomeContent()

        assertEquals(0.0, result.score, 0.001)
    }

    @Test
    fun searchContent_toHomeContent_withNonNumericOptionalInts_defaultsToNull() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "nope",
            language = "en",
            name = "Name",
            podcastId = "id",
            popularityScore = "nope",
            priority = "nope",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertNull(result.episodeCount)
        assertNull(result.popularityScore)
        assertNull(result.priority)
    }

    @Test
    fun searchContent_toHomeContent_withBlankLanguage_mapsToNull() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "5",
            language = "",
            name = "Name",
            podcastId = "id",
            popularityScore = "10",
            priority = "1",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertNull(result.language)
    }

    @Test
    fun searchContent_toHomeContent_withBlankPodcastId_mapsToNull() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "5",
            language = "en",
            name = "Name",
            podcastId = "",
            popularityScore = "10",
            priority = "1",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertNull(result.podcastId)
    }

    @Test
    fun searchContent_toHomeContent_withWhitespaceOnlyLanguage_mapsToNull() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "5",
            language = "   ",
            name = "Name",
            podcastId = "id",
            popularityScore = "10",
            priority = "1",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertNull(result.language)
    }

    @Test
    fun searchContent_toHomeContent_withNonBlankLanguageAndPodcastId_preservesValues() {
        val searchContent = SearchContent(
            avatarUrl = "url",
            description = "desc",
            duration = "100",
            episodeCount = "5",
            language = "ar",
            name = "Name",
            podcastId = "pod-789",
            popularityScore = "10",
            priority = "1",
            score = "4.0"
        )

        val result = searchContent.toHomeContent()

        assertEquals("ar", result.language)
        assertEquals("pod-789", result.podcastId)
    }
}
