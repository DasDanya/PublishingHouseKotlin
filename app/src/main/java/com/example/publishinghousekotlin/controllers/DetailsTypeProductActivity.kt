package com.example.publishinghousekotlin.controllers

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import com.example.publishinghousekotlin.databinding.DetailsTypeProductBinding
import com.example.publishinghousekotlin.dialogs.DeleteTypeProductDialog
import com.example.publishinghousekotlin.models.TypeProduct

class DetailsTypeProductActivity: AppCompatActivity() {

    private lateinit var detailsTypeProductBinding: DetailsTypeProductBinding
    private var typeProduct:TypeProduct? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsTypeProductBinding = DetailsTypeProductBinding.inflate(layoutInflater)
        setContentView(detailsTypeProductBinding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        setStartData()

        detailsTypeProductBinding.editBtn.setOnClickListener {
            val intent = Intent(this@DetailsTypeProductActivity, SaveTypeProductActivity::class.java)
            intent.putExtra("typeProduct", typeProduct)
            startActivity(intent)
        }

        detailsTypeProductBinding.deleteBtn.setOnClickListener {
            DeleteTypeProductDialog(typeProduct!!.id,detailsTypeProductBinding.root).show(supportFragmentManager, "DELETETYPEPRODUCTDIALOG")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setStartData(){
        typeProduct = intent.getSerializableExtra("typeProduct") as? TypeProduct

        val type = SpannableStringBuilder().bold { append("Тип: ") }.append(typeProduct?.type)
        val margin = SpannableStringBuilder().bold { append("Наценка в %: ") }.append(typeProduct?.margin.toString())

        detailsTypeProductBinding.typeTextView.text = type
        detailsTypeProductBinding.marginTextView.text = margin
    }

}