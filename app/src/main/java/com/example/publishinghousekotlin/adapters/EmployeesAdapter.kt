package com.example.publishinghousekotlin.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewBinding
import com.example.publishinghousekotlin.models.EmployeeDTO
import java.util.Base64

class EmployeesAdapter(private val clickListener: OnItemClickListener): PagingDataAdapter<EmployeeDTO, EmployeesAdapter.Holder>(COMPARATOR) {
    inner class Holder(val itemRecyclerViewBinding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(itemRecyclerViewBinding.root), View.OnClickListener{
        init {
            itemRecyclerViewBinding.root.setOnClickListener(this)
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

        with(holder.itemRecyclerViewBinding){
            if(employeeDTO.patronymic.isEmpty()){
                mainTextView.text = employeeDTO.surname + " " + employeeDTO.name[0] + "."
            } else{
                mainTextView.text = employeeDTO.surname + " " + employeeDTO.name[0] + "." + employeeDTO.patronymic[0] + "."
            }

            subTextView.text = employeeDTO.post

            val photo = Base64.getDecoder().decode(employeeDTO.photo)
            val bitmap = BitmapFactory.decodeByteArray(photo, 0 , photo.size)
            iconView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewBinding.inflate(inflater, parent,false)

        return Holder(binding)
    }

    fun getEmployeeDTO(position: Int):EmployeeDTO?{
        return getItem(position)
    }

}