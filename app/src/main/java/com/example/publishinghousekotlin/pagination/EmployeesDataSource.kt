package com.example.publishinghousekotlin.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.publishinghousekotlin.models.EmployeeDTO
import com.example.publishinghousekotlin.repositories.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeesDataSource(private var surname: String): PagingSource<Int, EmployeeDTO>() {
    override fun getRefreshKey(state: PagingState<Int, EmployeeDTO>): Int? {
        val anchorPosition = state.anchorPosition?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EmployeeDTO> {
        val pageIndex = params.key ?: 0

        return try{
            val employees = withContext(Dispatchers.IO){
                EmployeeRepository().get(pageIndex, surname)
            }

            return LoadResult.Page(
                data = employees!!,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                nextKey = if(employees.isNotEmpty()) pageIndex + 1 else null
            )
        } catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }

}