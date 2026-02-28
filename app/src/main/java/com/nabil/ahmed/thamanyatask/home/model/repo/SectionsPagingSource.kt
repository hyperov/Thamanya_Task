package com.nabil.ahmed.thamanyatask.home.model.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nabil.ahmed.thamanyatask.home.model.res.Section

class SectionsPagingSource(
    private val api: HomeApis
) : PagingSource<Int, Section>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Section> {
        val page = params.key ?: 1
        return try {
            val response = api.getHomeSections(page)
            val sections = response.sections.sortedBy { it.order }
            LoadResult.Page(
                data = sections,
                prevKey = null,
                nextKey = if (page < response.pagination.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Section>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}
