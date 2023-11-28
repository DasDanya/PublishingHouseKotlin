package com.example.publishinghousekotlin.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class TypeProduct(

    val id: Long,
    var type: String,
    var margin: Double
): Serializable {

    constructor() : this(0, "", 0.0)

}