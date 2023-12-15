package com.example.publishinghousekotlin.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.databinding.ItemRecyclerViewWithAddBinding
import com.example.publishinghousekotlin.models.Material

class RecyclerViewMaterialsAddAdapter(private val materials: List<Material>) : RecyclerView.Adapter<RecyclerViewMaterialsAddAdapter.ViewHolder>()  {

   inner class ViewHolder(val itemRecyclerViewWithAddBinding: ItemRecyclerViewWithAddBinding):RecyclerView.ViewHolder(itemRecyclerViewWithAddBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewMaterialsAddAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewWithAddBinding.inflate(inflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewMaterialsAddAdapter.ViewHolder, position: Int) {
        val material = materials[position]

        with(holder.itemRecyclerViewWithAddBinding){
            iconView.setImageResource(R.drawable.material)
            mainTextView.text = material.type
            subTextView.text = "Стоимость за 1 шт: " + material.cost + " ₽"
        }
    }

    override fun getItemCount(): Int {
        return materials.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getMaterial(position: Int):Material{
        return materials[position]
    }

}