package com.nabil.ahmed.thamanyatask.home.model.repo

import androidx.paging.testing.asSnapshot
import com.nabil.ahmed.thamanyatask.home.model.res.Content
import com.nabil.ahmed.thamanyatask.home.model.res.Pagination
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class HomeRepoImplTest {

    private val api: HomeApis = mockk()
    private val repo = HomeRepoImpl(api)

    private fun section(order: Int, name: String) = Section(
        content = listOf(
            Content(
                avatarUrl = "url",
                description = "desc",
                duration = 100,
                name = name,
                score = 4.0
            )
        ),
        contentType = "podcast",
        name = name,
        order = order,
        type = "square"
    )

    @Test
    fun getSectionsPaging_returnsNonNullFlow() {
        assertNotNull(repo.getSectionsPaging())
    }

    @Test
    fun getSectionsPaging_collectingTriggersApiCall() = runTest {
        val response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = listOf(section(1, "A"))
        )
        coEvery { api.getHomeSections(any()) } returns response

        repo.getSectionsPaging().asSnapshot()

        coVerify { api.getHomeSections(1) }
    }

    @Test
    fun getSectionsPaging_emitsSectionsSortedByOrder() = runTest {
        val response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = listOf(section(3, "Third"), section(1, "First"), section(2, "Second"))
        )
        coEvery { api.getHomeSections(1) } returns response

        val items = repo.getSectionsPaging().asSnapshot()

        assertEquals(3, items.size)
        assertEquals("First", items[0].name)
        assertEquals("Second", items[1].name)
        assertEquals("Third", items[2].name)
    }

    @Test
    fun getSectionsPaging_pagesThrough_multiplePages() = runTest {
        val page1Response = SectionsRes(
            pagination = Pagination(nextPage = "2", totalPages = 2),
            sections = listOf(section(1, "Page1-Item"))
        )
        val page2Response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 2),
            sections = listOf(section(1, "Page2-Item"))
        )
        coEvery { api.getHomeSections(1) } returns page1Response
        coEvery { api.getHomeSections(2) } returns page2Response

        val items = repo.getSectionsPaging().asSnapshot {
            scrollTo(index = 1)
        }

        assertEquals(2, items.size)
        assertEquals("Page1-Item", items[0].name)
        assertEquals("Page2-Item", items[1].name)
        coVerify(exactly = 1) { api.getHomeSections(1) }
        coVerify(exactly = 1) { api.getHomeSections(2) }
    }

    @Test
    fun getSectionsPaging_whenApiReturnsEmpty_emitsNoItems() = runTest {
        val response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = emptyList()
        )
        coEvery { api.getHomeSections(any()) } returns response

        val items = repo.getSectionsPaging().asSnapshot()

        assertEquals(0, items.size)
    }

    @Test
    fun getSectionsPaging_respectsPageSize_byLoadingSinglePagePerRequest() = runTest {
        val largePage = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = (1..10).map { section(it, "Item$it") }
        )
        coEvery { api.getHomeSections(1) } returns largePage

        val items = repo.getSectionsPaging().asSnapshot()

        assertEquals(10, items.size)
        coVerify(exactly = 1) { api.getHomeSections(1) }
    }
}
