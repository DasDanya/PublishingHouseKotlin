package com.example.publishinghousekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewTwoSubsBinding
import com.example.publishinghousekotlin.dtos.ProductMaterialDTO

class RecyclerViewWithCountMaterialsAdapter(private val materialsWithCount: List<ProductMaterialDTO>): RecyclerView.Adapter<RecyclerViewWithCountMaterialsAdapter.ViewHolder>() {

    inner class ViewHolder(val itemRecyclerViewTwoSubsBinding: ItemRecyclerViewTwoSubsBinding): RecyclerView.ViewHolder(itemRecyclerViewTwoSubsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewTwoSubsBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materialWithCount = materialsWithCount[position]

        with(holder.itemRecyclerViewTwoSubsBinding){
            iconView.setImageResource(R.drawable.material)
            mainTextView.text = materialWithCount.material.type
            firstSubTextView.text = "Стоимость за 1 шт: ${materialWithCount.material.cost} ₽"
            secondSubTextView.text = "Количество: ${materialWithCount.countMaterials} шт"
        }
    }

    override fun getItemCount(): Int {
        return materialsWithCount.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}