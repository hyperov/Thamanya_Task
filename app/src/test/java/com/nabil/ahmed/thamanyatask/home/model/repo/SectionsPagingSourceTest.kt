package com.nabil.ahmed.thamanyatask.home.model.repo

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nabil.ahmed.thamanyatask.home.model.res.Content
import com.nabil.ahmed.thamanyatask.home.model.res.Pagination
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SectionsPagingSourceTest {

    private val api: HomeApis = mockk()
    private val pagingSource = SectionsPagingSource(api)

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
    fun load_whenKeyIsNull_usesPage1() = runTest {
        val sections = listOf(section(1, "First"))
        val response = SectionsRes(
            pagination = Pagination(nextPage = "2", totalPages = 2),
            sections = sections
        )
        coEvery { api.getHomeSections(1) } returns response

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(1, page.data.size)
        assertEquals("First", page.data[0].name)
        assertNull(page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @Test
    fun load_firstPage_returnsPrevKeyNullAndNextKey2WhenTotalPagesGreaterThan1() = runTest {
        val sections = listOf(section(1, "A"))
        val response = SectionsRes(
            pagination = Pagination(nextPage = "2", totalPages = 3),
            sections = sections
        )
        coEvery { api.getHomeSections(1) } returns response

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params) as PagingSource.LoadResult.Page<Int, Section>

        assertNull(result.prevKey)
        assertEquals(2, result.nextKey)
    }

    @Test
    fun load_lastPage_returnsNextKeyNull() = runTest {
        val sections = listOf(section(2, "B"))
        val response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 2),
            sections = sections
        )
        coEvery { api.getHomeSections(2) } returns response

        val params = PagingSource.LoadParams.Append<Int>(
            key = 2,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params) as PagingSource.LoadResult.Page<Int, Section>

        assertEquals(1, result.data.size)
        assertNull(result.nextKey)
    }

    @Test
    fun load_sortsSectionsByOrder() = runTest {
        val unsorted = listOf(
            section(3, "Third"),
            section(1, "First"),
            section(2, "Second")
        )
        val response = SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = unsorted
        )
        coEvery { api.getHomeSections(1) } returns response

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params) as PagingSource.LoadResult.Page<Int, Section>

        assertEquals(3, result.data.size)
        assertEquals("First", result.data[0].name)
        assertEquals("Second", result.data[1].name)
        assertEquals("Third", result.data[2].name)
    }

    @Test
    fun load_whenApiThrows_returnsLoadResultError() = runTest {
        coEvery { api.getHomeSections(1) } throws RuntimeException("Network error")

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params)

        assertTrue(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assertTrue(error.throwable is RuntimeException)
        assertEquals("Network error", error.throwable.message)
    }

    @Test
    fun getRefreshKey_whenAnchorPositionAndPageExist_returnsKeyForRefresh() {
        val page = PagingSource.LoadResult.Page<Int, Section>(
            data = listOf(section(1, "A")),
            prevKey = null,
            nextKey = 2
        )
        val state = PagingState<Int, Section>(
            pages = listOf(page),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(state)

        assertEquals(1, refreshKey)
    }

    @Test
    fun getRefreshKey_whenNoAnchorPosition_returnsNull() {
        val state = PagingState<Int, Section>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(state)

        assertNull(refreshKey)
    }
}
