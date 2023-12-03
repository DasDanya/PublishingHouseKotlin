package com.example.publishinghousekotlin.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.publishinghousekotlin.models.PrintingHouse
import com.example.publishinghousekotlin.repositories.MaterialRepository
import com.example.publishinghousekotlin.repositories.PrintingHouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PrintingHousesDataSource(private var name: String): PagingSource<Int,PrintingHouse>() {
    override fun getRefreshKey(state: PagingState<Int, PrintingHouse>): Int? {
        val anchorPosition = state.anchorPosition?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PrintingHouse> {
        val pageIndex = params.key ?: 0

        return try{
            val printingHouses = withContext(Dispatchers.IO){
                PrintingHouseRepository().get(pageIndex, name)
            }

            return LoadResult.Page(
                data = printingHouses!!,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                nextKey = if(printingHouses.isNotEmpty()) pageIndex + 1 else null
            )
        } catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }
}