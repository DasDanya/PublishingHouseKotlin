package com.example.publishinghousekotlin.ui.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.models.Material
import com.example.publishinghousekotlin.repositories.MaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MaterialViewModel : ViewModel() {

    private val searchType = MutableLiveData<String>("")

    val listOfMaterials: LiveData<PagingData<Material>> = searchType.switchMap { query ->
        MaterialRepository().getPagedMaterials(query).cachedIn(viewModelScope)
    }

    fun updateSearchType(query: String){
        searchType.value = query
    }
}