package com.example.publishinghousekotlin.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.dtos.EmployeeDTO
import com.example.publishinghousekotlin.repositories.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EmployeeViewModel: ViewModel() {

    private val searchType = MutableLiveData<String>("")

    val listOfEmployees: LiveData<PagingData<EmployeeDTO>> = searchType.switchMap { query ->
        EmployeeRepository().getPagedEmployees(query).cachedIn(viewModelScope)
    }

    fun updateSearchType(query: String){
        searchType.value = query
    }
}