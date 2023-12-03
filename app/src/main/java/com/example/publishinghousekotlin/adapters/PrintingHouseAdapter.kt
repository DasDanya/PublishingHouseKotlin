package com.example.publishinghousekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.OnItemClickListener
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewBinding
import com.example.publishinghousekotlin.models.PrintingHouse

class PrintingHouseAdapter(private val clickListener: OnItemClickListener): PagingDataAdapter<PrintingHouse, PrintingHouseAdapter.Holder>(COMPARATOR) {

    inner class Holder(val itemRecyclerViewBinding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(itemRecyclerViewBinding.root), View.OnClickListener{
        init {
            itemRecyclerViewBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onItemClick(adapterPosition)
        }
    }

    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<PrintingHouse>(){
            override fun areItemsTheSame(oldItem: PrintingHouse, newItem: PrintingHouse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PrintingHouse, newItem: PrintingHouse): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val printingHouse = getItem(position) ?: return

        with(holder.itemRecyclerViewBinding){
            iconView.setImageResource(R.drawable.printing_house)
            mainTextView.text = printingHouse.name
            subTextView.text = printingHouse.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewBinding.inflate(inflater, parent, false)

        return Holder(binding)
    }

    fun getPrintingHouse(position: Int): PrintingHouse?{
        return getItem(position)
    }
}