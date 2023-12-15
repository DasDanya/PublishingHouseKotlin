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
import com.example.publishinghousekotlin.dtos.ProductAcceptDTO

class ProductsAdapter(private val clickListener: OnItemClickListener): PagingDataAdapter<ProductAcceptDTO, ProductsAdapter.Holder>(COMPARATOR) {

    inner class Holder(val largeItemRecyclerViewBinding: LargeItemRecyclerViewBinding): RecyclerView.ViewHolder(largeItemRecyclerViewBinding.root), View.OnClickListener{

        init {
            largeItemRecyclerViewBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onItemClick(adapterPosition)
        }
    }

    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<ProductAcceptDTO>(){
            override fun areItemsTheSame(oldItem: ProductAcceptDTO, newItem: ProductAcceptDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductAcceptDTO, newItem: ProductAcceptDTO): Boolean {
                return oldItem == newItem
            }


        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val productDTO = getItem(position) ?: return

        with(holder.largeItemRecyclerViewBinding){
            mainTextView.text = productDTO.name
            subTextView.text = "Стоимость за 1 экземпляр: " + productDTO.cost + " ₽"
            iconView.setImageBitmap(FileWorker().getBitmap(productDTO.photos!![0]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LargeItemRecyclerViewBinding.inflate(inflater,parent,false)

        return Holder(binding)
    }

    fun getProductDTO(position: Int): ProductAcceptDTO?{
        return getItem(position)
    }
}