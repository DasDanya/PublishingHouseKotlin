package com.example.publishinghousekotlin.ui.product

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
import com.example.publishinghousekotlin.adapters.ProductsAdapter
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.controllers.DetailsProductActivity
import com.example.publishinghousekotlin.databinding.FragmentGeneralBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment: Fragment(), OnItemClickListener {

    private var _fragmentProductsBinding: FragmentGeneralBinding? = null
    private val fragmentProductsBinding get() = _fragmentProductsBinding!!

    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        _fragmentProductsBinding = FragmentGeneralBinding.inflate(inflater, container, false)
        fragmentProductsBinding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        fragmentProductsBinding.searchTextInputLayout.hint = "Поиск по наименованию"

        adapter = ProductsAdapter(this)
        fragmentProductsBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        fragmentProductsBinding.recyclerView.setHasFixedSize(true)
        fragmentProductsBinding.recyclerView.adapter = adapter

        productViewModel.listOfProducts.observe(this.viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }

        fragmentProductsBinding.searchEditText.addTextChangedListener {
            productViewModel.updateSearchType(fragmentProductsBinding.searchEditText.text.toString().trim())
        }

        return fragmentProductsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentProductsBinding = null
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailsProductActivity::class.java)
        intent.putExtra("productId", adapter.getProductDTO(position)!!.id)
        startActivity(intent)
    }
}