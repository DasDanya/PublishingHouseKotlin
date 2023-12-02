package com.example.publishinghousekotlin.ui.Material

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.publishinghousekotlin.adapters.MaterialsAdapter
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.controllers.DetailsMaterialActivity
import com.example.publishinghousekotlin.databinding.FragmentGeneralBinding
import com.example.publishinghousekotlin.databinding.FragmentSlideshowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MaterialFragment : Fragment(), OnItemClickListener {

    private var _fragmentMaterialsBinding: FragmentGeneralBinding? = null
    private val fragmentMaterialsBinding get() = _fragmentMaterialsBinding!!

    private lateinit var materialViewModel: MaterialViewModel
    private lateinit var adapter: MaterialsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        materialViewModel = ViewModelProvider(this)[MaterialViewModel::class.java]

        _fragmentMaterialsBinding = FragmentGeneralBinding.inflate(inflater, container, false)
        fragmentMaterialsBinding.recyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        fragmentMaterialsBinding.searchTextInputLayout.hint = "Поиск по типу"

        adapter = MaterialsAdapter(this)
        fragmentMaterialsBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        fragmentMaterialsBinding.recyclerView.setHasFixedSize(true)
        fragmentMaterialsBinding.recyclerView.adapter = adapter

        materialViewModel.listOfMaterials.observe(this.viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }

        fragmentMaterialsBinding.searchEditText.addTextChangedListener {
            materialViewModel.updateSearchType(fragmentMaterialsBinding.searchEditText.text.toString().trim())
        }

        return fragmentMaterialsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMaterialsBinding = null
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailsMaterialActivity::class.java)
        intent.putExtra("material", adapter.getMaterial(position))
        startActivity(intent)
    }
}