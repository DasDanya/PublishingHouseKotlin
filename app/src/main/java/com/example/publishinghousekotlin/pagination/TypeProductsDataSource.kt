package com.example.publishinghousekotlin.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.publishinghousekotlin.models.TypeProduct
import com.example.publishinghousekotlin.repositories.TypeProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TypeProductsDataSource: PagingSource<Int, TypeProduct>() {
    override fun getRefreshKey(state: PagingState<Int, TypeProduct>): Int? {
        val anchorPosition = state.anchorPosition?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TypeProduct> {
        val pageIndex = params.key ?: 0

        return try{
                val typeProducts = withContext(Dispatchers.IO){
                    TypeProductRepository().get(pageIndex)
                }

            return LoadResult.Page(
                data = typeProducts!!,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                //nextKey = if(typeProducts.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
                nextKey = if(typeProducts.isNotEmpty()) pageIndex + 1 else null
            )
        } catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }
}