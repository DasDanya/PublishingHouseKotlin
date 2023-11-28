package com.example.publishinghousekotlin.ui.typeProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.publishinghousekotlin.adapters.TypeProductsAdapter
import com.example.publishinghousekotlin.databinding.FragmentTypeProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeProductFragment : Fragment() {

    private var _homeBinding: FragmentTypeProductsBinding? = null
    private val homeBinding get() = _homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val typeProductViewModel = ViewModelProvider(this)[TypeProductViewModel::class.java]
        _homeBinding = FragmentTypeProductsBinding.inflate(inflater, container, false)

        val adapter = TypeProductsAdapter()
        homeBinding.typeProductsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        homeBinding.typeProductsRecyclerView.setHasFixedSize(true)
        homeBinding.typeProductsRecyclerView.adapter = adapter

        typeProductViewModel!!.list.observe(this.viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        return homeBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }
}