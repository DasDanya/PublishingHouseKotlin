package com.example.publishinghousekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewTwoSubsBinding
import com.example.publishinghousekotlin.dtos.CountProductsDTO

class CountProductsAdapter(private val countProductsDTOS: List<CountProductsDTO>): RecyclerView.Adapter<CountProductsAdapter.ViewHolder>() {

    inner class ViewHolder(val itemRecyclerViewTwoSubsBinding: ItemRecyclerViewTwoSubsBinding): RecyclerView.ViewHolder(itemRecyclerViewTwoSubsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewTwoSubsBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countProductsDTOS.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countProductsDTO = countProductsDTOS[position]

        with(holder.itemRecyclerViewTwoSubsBinding){
            iconView.setImageResource(R.drawable.booking)
            mainTextView.text = "Заказ №${countProductsDTO.booking.id}"
            firstSubTextView.text = "Стоимость выполнения: ${countProductsDTO.booking.cost} ₽"
            secondSubTextView.text = "Количество продукции: ${countProductsDTO.margin}"

        }
    }


}