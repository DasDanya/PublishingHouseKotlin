package com.example.publishinghousekotlin.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.publishinghousekotlin.dtos.ProductAcceptDTO
import com.example.publishinghousekotlin.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsDataSource(private var name: String): PagingSource<Int, ProductAcceptDTO>() {
    override fun getRefreshKey(state: PagingState<Int, ProductAcceptDTO>): Int? {
        val anchorPosition = state.anchorPosition?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductAcceptDTO> {
        val pageIndex = params.key ?: 0

        return try{
            val products = withContext(Dispatchers.IO){
                ProductRepository().get(pageIndex, name)
            }

            return LoadResult.Page(
                data = products!!,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                nextKey = if(products.isNotEmpty()) pageIndex + 1 else null
            )
        } catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }

}
