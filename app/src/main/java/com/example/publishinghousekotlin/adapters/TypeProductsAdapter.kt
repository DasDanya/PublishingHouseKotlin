package com.example.publishinghousekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewBinding


import com.example.publishinghousekotlin.models.TypeProduct

class TypeProductsAdapter: PagingDataAdapter<TypeProduct, TypeProductsAdapter.Holder> (COMPARATOR) {

    inner class Holder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<TypeProduct>(){
            override fun areItemsTheSame(oldItem: TypeProduct, newItem: TypeProduct): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TypeProduct, newItem: TypeProduct): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val typeProduct = getItem(position) ?: return

        with(holder.binding){
            iconView.setImageResource(R.drawable.type_product)
            mainTextView.text = typeProduct.type
            subTextView.text = "Наценка: " + typeProduct.margin.toString() + "%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewBinding.inflate(inflater, parent,false)
        return Holder(binding)
    }
}