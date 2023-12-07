package com.example.publishinghousekotlin.ui.employee

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
import com.example.publishinghousekotlin.adapters.EmployeesAdapter
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.controllers.DetailsEmployeeActivity
import com.example.publishinghousekotlin.databinding.FragmentGeneralBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment: Fragment(), OnItemClickListener {

    private var _fragmentEmployeesBinding: FragmentGeneralBinding? = null
    private val fragmentEmployeesBinding get() = _fragmentEmployeesBinding!!

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var adapter: EmployeesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        employeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]

        _fragmentEmployeesBinding = FragmentGeneralBinding.inflate(inflater,container, false)
        fragmentEmployeesBinding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        fragmentEmployeesBinding.searchTextInputLayout.hint = "Поиск по фамилии"

        adapter = EmployeesAdapter(this)
        fragmentEmployeesBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        fragmentEmployeesBinding.recyclerView.setHasFixedSize(true)
        fragmentEmployeesBinding.recyclerView.adapter = adapter

        employeeViewModel.listOfEmployees.observe(this.viewLifecycleOwner){
            adapter.submitData(lifecycle,it)
        }

        fragmentEmployeesBinding.searchEditText.addTextChangedListener {
            employeeViewModel.updateSearchType(fragmentEmployeesBinding.searchEditText.text.toString().trim())
        }

        return fragmentEmployeesBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentEmployeesBinding = null
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailsEmployeeActivity::class.java)
        intent.putExtra("employee", adapter.getEmployeeDTO(position))
        startActivity(intent)
    }
}