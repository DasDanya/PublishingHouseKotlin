package com.example.publishinghousekotlin.ui.printingHouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.publishinghousekotlin.models.PrintingHouse
import com.example.publishinghousekotlin.repositories.PrintingHouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PrintingHouseViewModel: ViewModel() {

    private val searchType = MutableLiveData<String>("")

    val listOfPrintingHouses: LiveData<PagingData<PrintingHouse>> = searchType.switchMap { query ->
        PrintingHouseRepository().getPagedPrintingHouses(query).cachedIn(viewModelScope)
    }

    fun updateSearchType(query: String){
        searchType.value = query
    }
}