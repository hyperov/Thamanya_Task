package com.nabil.ahmed.thamanyatask.home.viewmodel

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.Pager
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeRepo
import com.nabil.ahmed.thamanyatask.home.model.repo.HomeApis
import com.nabil.ahmed.thamanyatask.home.model.repo.SectionsPagingSource
import com.nabil.ahmed.thamanyatask.home.model.res.Content
import com.nabil.ahmed.thamanyatask.home.model.res.Pagination
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.home.model.res.SectionsRes
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomePaginationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val homeRepo: HomeRepo = mockk()
    private lateinit var viewModel: HomePaginationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun sectionsPaging_isNotNull() {
        every { homeRepo.getSectionsPaging() } returns flowFromPagerWithMockApi()
        viewModel = HomePaginationViewModel(homeRepo)

        assertNotNull(viewModel.sectionsPaging)
    }

    @Test
    fun sectionsPaging_delegatesToHomeRepoGetSectionsPaging() = runTest {
        every { homeRepo.getSectionsPaging() } returns flowFromPagerWithMockApi()
        viewModel = HomePaginationViewModel(homeRepo)

        viewModel.sectionsPaging.first()

        verify(atLeast = 1) { homeRepo.getSectionsPaging() }
    }

    @Test
    fun sectionsPaging_whenCollected_emitsPagingDataFromRepo() = runTest {
        every { homeRepo.getSectionsPaging() } returns flowFromPagerWithMockApi()
        viewModel = HomePaginationViewModel(homeRepo)

        val pagingData = viewModel.sectionsPaging.first()

        assertNotNull(pagingData)
    }

    private fun flowFromPagerWithMockApi(): kotlinx.coroutines.flow.Flow<PagingData<Section>> {
        val api: HomeApis = mockk()
        io.mockk.coEvery { api.getHomeSections(1) } returns SectionsRes(
            pagination = Pagination(nextPage = "", totalPages = 1),
            sections = listOf(
                Section(
                    content = listOf(
                        Content(
                            avatarUrl = "url",
                            description = "desc",
                            duration = 100,
                            name = "Name",
                            score = 4.0
                        )
                    ),
                    contentType = "podcast",
                    name = "Section",
                    order = 1,
                    type = "square"
                )
            )
        )
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { SectionsPagingSource(api) }
        ).flow
    }
}
