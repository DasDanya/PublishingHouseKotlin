package com.example.publishinghousekotlin.ui.printingHouse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.publishinghousekotlin.adapters.PrintingHouseAdapter
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.controllers.DetailsPrintingHouseActivity
import com.example.publishinghousekotlin.databinding.FragmentGeneralBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrintingHouseFragment: Fragment(), OnItemClickListener {

    private var _fragmentPrintingHousesBinding: FragmentGeneralBinding? = null
    private val fragmentPrintingHousesBinding get() = _fragmentPrintingHousesBinding!!

    private lateinit var printingHouseViewModel: PrintingHouseViewModel
    private lateinit var adapter: PrintingHouseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        printingHouseViewModel = ViewModelProvider(this)[PrintingHouseViewModel::class.java]

        _fragmentPrintingHousesBinding = FragmentGeneralBinding.inflate(inflater,container,false)
        fragmentPrintingHousesBinding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        fragmentPrintingHousesBinding.searchTextInputLayout.hint = "Поиск по названию"

        adapter = PrintingHouseAdapter(this)
        fragmentPrintingHousesBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        fragmentPrintingHousesBinding.recyclerView.setHasFixedSize(true)
        fragmentPrintingHousesBinding.recyclerView.adapter = adapter

        printingHouseViewModel.listOfPrintingHouses.observe(this.viewLifecycleOwner){
            adapter.submitData(lifecycle,it)
        }

        fragmentPrintingHousesBinding.searchEditText.addTextChangedListener {
            printingHouseViewModel.updateSearchType(fragmentPrintingHousesBinding.searchEditText.text.toString().trim())
        }


        return fragmentPrintingHousesBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentPrintingHousesBinding = null
    }

    override fun onItemClick(position: Int) {

        val intent = Intent(activity, DetailsPrintingHouseActivity::class.java)
        intent.putExtra("printingHouse", adapter.getPrintingHouse(position))
        startActivity(intent)
    }
}