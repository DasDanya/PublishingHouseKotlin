package com.example.publishinghousekotlin.ui.typeProduct


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.models.TypeProduct
import com.example.publishinghousekotlin.repositories.TypeProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel



@HiltViewModel
class TypeProductViewModel : ViewModel() {

    private val searchType = MutableLiveData<String>("")

    val listOfTypeProducts: LiveData<PagingData<TypeProduct>> = searchType.switchMap { query ->
        TypeProductRepository().getPagedTypeProducts(query).cachedIn(viewModelScope)
    }
    fun updateSearchType(query: String) {
        searchType.value = query
    }

    //var list = TypeProductRepository().getPagedTypeProducts(currentQuery).cachedIn(viewModelScope)

//    private val _originalList = TypeProductRepository().getPagedTypeProducts().cachedIn(viewModelScope)
//    private val _list = MediatorLiveData<PagingData<TypeProduct>>()
//    val list: LiveData<PagingData<TypeProduct>> get() = _list
//
//    init {
//        // Подключение оригинального источника данных
//        _list.addSource(_originalList) {
//            _list.value = it
//        }
//    }


}