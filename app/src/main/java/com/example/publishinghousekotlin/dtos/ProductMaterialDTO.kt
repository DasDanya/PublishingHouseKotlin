package com.example.publishinghousekotlin.dtos

import com.example.publishinghousekotlin.models.Material
import java.io.Serializable

data class ProductMaterialDTO(
    val material: Material,
    val countMaterials: Int
){
}