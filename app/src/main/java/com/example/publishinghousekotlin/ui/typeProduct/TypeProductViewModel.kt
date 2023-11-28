package com.example.publishinghousekotlin.ui.typeProduct


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.repositories.TypeProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel



@HiltViewModel
class TypeProductViewModel : ViewModel() {


    var list = TypeProductRepository().getPagedTypeProducts().cachedIn(viewModelScope)

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