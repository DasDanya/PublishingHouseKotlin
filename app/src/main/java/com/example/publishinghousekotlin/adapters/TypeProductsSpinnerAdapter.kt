package com.example.publishinghousekotlin.adapters

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.publishinghousekotlin.models.TypeProduct

class TypeProductsSpinnerAdapter(context: Context, typeProducts: List<TypeProduct>): ArrayAdapter<TypeProduct>(context, R.layout.simple_spinner_dropdown_item, typeProducts) {

    override fun getView(position: Int, convertView: View?, parent:ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val typeProduct = getItem(position)
        (view as TextView).text = typeProduct?.type
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val typeProduct = getItem(position)
        (view as TextView).text = typeProduct?.type
        return view
    }

    fun getSelectedTypeProduct(position: Int):TypeProduct?{
        return getItem(position)
    }
}