package com.example.publishinghousekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.basics.FileWorker
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.databinding.LargeItemRecyclerViewBinding
import com.example.publishinghousekotlin.models.EmployeeDTO

class EmployeesAdapter(private val clickListener: OnItemClickListener): PagingDataAdapter<EmployeeDTO, EmployeesAdapter.Holder>(COMPARATOR) {
    inner class Holder(val largeItemRecyclerViewBinding: LargeItemRecyclerViewBinding): RecyclerView.ViewHolder(largeItemRecyclerViewBinding.root), View.OnClickListener{
        init {
            largeItemRecyclerViewBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onItemClick(adapterPosition)
        }

    }

    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<EmployeeDTO>(){
            override fun areItemsTheSame(oldItem: EmployeeDTO, newItem: EmployeeDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EmployeeDTO, newItem: EmployeeDTO): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val employeeDTO = getItem(position) ?: return

        with(holder.largeItemRecyclerViewBinding){
            if(employeeDTO.patronymic.isEmpty()){
                mainTextView.text = employeeDTO.surname + " " + employeeDTO.name[0] + "."
            } else{
                mainTextView.text = employeeDTO.surname + " " + employeeDTO.name[0] + "." + employeeDTO.patronymic[0] + "."
            }

            subTextView.text = employeeDTO.post

            iconView.setImageBitmap(FileWorker().getBitmap(employeeDTO.photo))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LargeItemRecyclerViewBinding.inflate(inflater, parent,false)

        return Holder(binding)
    }

    fun getEmployeeDTO(position: Int):EmployeeDTO?{
        return getItem(position)
    }

}