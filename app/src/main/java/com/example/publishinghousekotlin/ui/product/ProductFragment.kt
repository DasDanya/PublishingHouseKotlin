package com.example.publishinghousekotlin.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.publishinghousekotlin.databinding.FragmentGeneralBinding

class ProductFragment: Fragment() {

    private var _fragmentProductsBinding: FragmentGeneralBinding? = null
    private val fragmentProductsBinding get() = _fragmentProductsBinding!!

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        _fragmentProductsBinding = FragmentGeneralBinding.inflate(inflater, container, false)

        return fragmentProductsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentProductsBinding = null
    }
}