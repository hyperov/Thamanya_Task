package com.nabil.ahmed.thamanyatask.search.viewmodel

import app.cash.turbine.test
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.search.model.repo.SearchRepo
import com.nabil.ahmed.thamanyatask.search.model.res.Content as SearchContent
import com.nabil.ahmed.thamanyatask.search.model.res.SearchRes
import com.nabil.ahmed.thamanyatask.search.model.res.SearchSection
import com.nabil.ahmed.thamanyatask.utils.ApiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val searchRepo: SearchRepo = mockk()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(searchRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun searchSections_initialState_isEmpty() = runTest {
        viewModel.searchSections.test {
            assertEquals(ApiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun updateQuery_withBlank_setsSearchSectionsToEmpty() = runTest {
        every { searchRepo.getSearchSections() } returns flow { emit(SearchRes(emptyList())) }
        viewModel.updateQuery("")
        assertEquals(ApiState.Empty, viewModel.searchSections.value)
    }

    @Test
    fun updateQuery_withNonBlank_emitsLoadingThenSuccessWithMappedSections() = runTest {
        val sections = listOf(
            SearchSection(
                content = listOf(
                    SearchContent(
                        avatarUrl = "url",
                        description = "desc",
                        duration = "100",
                        episodeCount = "5",
                        language = "en",
                        name = "Podcast",
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
        every { searchRepo.getSearchSections() } returns flow { emit(SearchRes(sections)) }

        viewModel.searchSections.test {
            skipItems(1)
            viewModel.updateQuery("test")
            advanceTimeBy(250)
            advanceUntilIdle()
            assertEquals(ApiState.Loading, awaitItem())
            val success = awaitItem()
            assert(success is ApiState.Success)
            val data = (success as ApiState.Success).data
            assertEquals(1, data.size)
            assertEquals("Section", data[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun updateQuery_whenRepoEmitsError_emitsLoadingThenError() = runTest {
        every { searchRepo.getSearchSections() } returns flow {
            throw RuntimeException("Network error")
        }

        viewModel.searchSections.test {
            skipItems(1)
            viewModel.updateQuery("q")
            advanceTimeBy(250)
            advanceUntilIdle()
            assertEquals(ApiState.Loading, awaitItem())
            val error = awaitItem()
            assert(error is ApiState.Error)
            assertEquals("Network error", (error as ApiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun query_updatesWhenUpdateQueryCalled() = runTest {
        every { searchRepo.getSearchSections() } returns flow { emit(SearchRes(emptyList())) }
        viewModel.query.test {
            assertEquals("", awaitItem())
            viewModel.updateQuery("hello")
            assertEquals("hello", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
