package com.nabil.ahmed.thamanyatask.search.model.repo

import com.nabil.ahmed.thamanyatask.search.model.res.Content as SearchContent
import com.nabil.ahmed.thamanyatask.search.model.res.SearchRes
import com.nabil.ahmed.thamanyatask.search.model.res.SearchSection
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchRepoImplTest {

    private val api: SearchApis = mockk()
    private val repo = SearchRepoImpl(api)

    @Test
    fun getSearchSections_whenApiReturnsSuccess_emitsSearchRes() = runTest {
        val sections = listOf(
            SearchSection(
                content = listOf(
                    SearchContent(
                        avatarUrl = "url",
                        description = "desc",
                        duration = "100",
                        episodeCount = "5",
                        language = "en",
                        name = "Name",
                        podcastId = "id",
                        popularityScore = "10",
                        priority = "1",
                        score = "4.0"
                    )
                ),
                contentType = "podcast",
                name = "Section",
                order = "1",
                type = "square"
            )
        )
        val expected = SearchRes(sections = sections)
        coEvery { api.getSearchSections() } returns expected

        val result = repo.getSearchSections().first()

        assertEquals(expected, result)
        assertEquals(1, result.sections.size)
        assertEquals("Section", result.sections[0].name)
    }

    @Test
    fun getSearchSections_whenApiReturnsEmptyList_emitsEmptySearchRes() = runTest {
        val expected = SearchRes(sections = emptyList())
        coEvery { api.getSearchSections() } returns expected

        val result = repo.getSearchSections().first()

        assertEquals(expected, result)
        assertEquals(0, result.sections.size)
    }

    @Test(expected = RuntimeException::class)
    fun getSearchSections_whenApiThrows_propagatesException() = runTest {
        coEvery { api.getSearchSections() } throws RuntimeException("Network error")

        repo.getSearchSections().first()
    }
}
