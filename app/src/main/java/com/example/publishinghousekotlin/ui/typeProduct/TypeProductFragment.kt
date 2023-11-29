package com.example.publishinghousekotlin.ui.typeProduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.publishinghousekotlin.adapters.TypeProductsAdapter
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.controllers.DetailsTypeProductActivity
import com.example.publishinghousekotlin.controllers.SaveTypeProductActivity
import com.example.publishinghousekotlin.databinding.FragmentTypeProductsBinding
import com.example.publishinghousekotlin.models.TypeProduct
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeProductFragment : Fragment(), OnItemClickListener {

    private var _homeBinding: FragmentTypeProductsBinding? = null
    private val homeBinding get() = _homeBinding!!

    private lateinit var typeProductViewModel: TypeProductViewModel
    private lateinit var adapter: TypeProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        typeProductViewModel = ViewModelProvider(this)[TypeProductViewModel::class.java]
        _homeBinding = FragmentTypeProductsBinding.inflate(inflater, container, false)
        homeBinding.typeProductsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter = TypeProductsAdapter(this)
        homeBinding.typeProductsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        homeBinding.typeProductsRecyclerView.setHasFixedSize(true)
        homeBinding.typeProductsRecyclerView.adapter = adapter

        typeProductViewModel.list.observe(this.viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        return homeBinding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    override fun onItemClick(position:Int) {

        val intent = Intent(activity, DetailsTypeProductActivity::class.java)
        intent.putExtra("typeProduct", adapter.getTypeProduct(position))
        startActivity(intent)
    }
}