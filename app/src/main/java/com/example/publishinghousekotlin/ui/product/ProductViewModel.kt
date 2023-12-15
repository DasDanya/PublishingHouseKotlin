package com.example.publishinghousekotlin.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.dtos.ProductAcceptDTO
import com.example.publishinghousekotlin.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ProductViewModel: ViewModel() {

    private val searchType = MutableLiveData<String>("")

    val listOfProducts: LiveData<PagingData<ProductAcceptDTO>> = searchType.switchMap { query ->
        ProductRepository().getPagedProducts(query).cachedIn(viewModelScope)
    }

    fun updateSearchType(query: String){
        searchType.value = query
    }
}