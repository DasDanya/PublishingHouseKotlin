package com.example.publishinghousekotlin.controllers

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.publishinghousekotlin.MyApplication
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.adapters.CountProductsAdapter
import com.example.publishinghousekotlin.adapters.RecyclerViewWithCountMaterialsAdapter
import com.example.publishinghousekotlin.basics.FileWorker
import com.example.publishinghousekotlin.basics.Messages
import com.example.publishinghousekotlin.databinding.DetailsProductBinding
import com.example.publishinghousekotlin.dialogs.DeleteProductDialog
import com.example.publishinghousekotlin.dtos.ProductAcceptDTO
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.example.publishinghousekotlin.models.UserRole
import com.example.publishinghousekotlin.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsProductActivity: AppCompatActivity() {

    private lateinit var detailsProductBinding: DetailsProductBinding
    private val message = Messages()
    private var product: ProductAcceptDTO? = null
    private var indexOfPhoto = 0
    private var materialsAreShown = false
    private var bookingsAreShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsProductBinding = DetailsProductBinding.inflate(layoutInflater)
        setContentView(detailsProductBinding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        detailsProductBinding.materialsRecyclerView.isVisible = false
        detailsProductBinding.bookingsRecyclerView.isVisible = false

        setStartData()

        detailsProductBinding.deleteBtn.setOnClickListener {
            DeleteProductDialog(product!!.id, detailsProductBinding.root).show(supportFragmentManager, "DELETEPRODUCTDIALOG")
        }

        detailsProductBinding.editBtn.setOnClickListener {
            val intent = Intent(this@DetailsProductActivity, SaveProductActivity::class.java)
            intent.putExtra("productId", product!!.id)
            startActivity(intent)
        }
    }

    private fun setStartData() {

        var productId = intent.getLongExtra("productId", 0)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                product = ProductRepository().get(productId)
                withContext(Dispatchers.Main){
                    if (product != null) {

                        // Список материалов
                        val materialsAdapter = RecyclerViewWithCountMaterialsAdapter(product!!.productMaterialDTOS!!)
                        detailsProductBinding.materialsRecyclerView.adapter = materialsAdapter
                        detailsProductBinding.materialsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        detailsProductBinding.materialsRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

                        detailsProductBinding.showMaterialsBtn.setOnClickListener {
                            if(materialsAreShown){
                                materialsAreShown = false
                                detailsProductBinding.materialsRecyclerView.isVisible = false
                                detailsProductBinding.showMaterialsBtn.text = applicationContext.resources.getString(R.string.showMaterials)
                            }else{
                                materialsAreShown = true
                                detailsProductBinding.materialsRecyclerView.isVisible = true
                                detailsProductBinding.showMaterialsBtn.text = applicationContext.resources.getString(R.string.hideMaterials)
                            }
                        }

                        // Список заказов
                        val countProductsAdapter = CountProductsAdapter(product!!.countProductsDTOS!!)
                        detailsProductBinding.bookingsRecyclerView.adapter = countProductsAdapter
                        detailsProductBinding.bookingsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        detailsProductBinding.bookingsRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

                        detailsProductBinding.showBookingsBtn.setOnClickListener {
                            if (bookingsAreShown) {
                                bookingsAreShown = false
                                detailsProductBinding.bookingsRecyclerView.isVisible = false
                                detailsProductBinding.showBookingsBtn.text = applicationContext.resources.getString(R.string.showBookings)
                            } else {
                                bookingsAreShown = true
                                detailsProductBinding.bookingsRecyclerView.isVisible = true
                                detailsProductBinding.showBookingsBtn.text = applicationContext.resources.getString(R.string.hideBookings)
                            }
                        }

                        // Общие данные
                        val name = SpannableStringBuilder().bold { append("Наименование: ") }.append(product!!.name)
                        val typeProductName = SpannableStringBuilder().bold { append("Тип продукции: ") }.append(product!!.typeProduct!!.type)
                        val cost = SpannableStringBuilder().bold { append("Стоимость в ₽: ") }.append(product!!.cost.toString())

                        detailsProductBinding.nameTextView.text = name
                        detailsProductBinding.typeProductTextView.text = typeProductName
                        detailsProductBinding.costTextView.text = cost

                        val userRole = JwtResponse.getFromMemory(applicationContext, MyApplication.instance.applicationContext.resources.getString(R.string.keyForJwtResponse))!!.user.role
                        if(userRole == UserRole.ADMINISTRATOR.name){

                            val username = SpannableStringBuilder().bold { append("Заказчик: ") }.append(product!!.username)
                            val userEmail = SpannableStringBuilder().bold { append("Почта заказчика: ") }.append(product!!.userEmail)

                            detailsProductBinding.userTextView.text = username
                            detailsProductBinding.userEmailTextView.text = userEmail

                            detailsProductBinding.editBtn.isVisible = false
                            detailsProductBinding.deleteBtn.isVisible = false

                        }else{
                            detailsProductBinding.userEmailTextView.isVisible = false
                            detailsProductBinding.userTextView.isVisible = false
                        }

                        // Фотки
                        setPhotoInView(0)

                        if (product!!.photos!!.size == 1) {
                            detailsProductBinding.backPhotoBtn.isVisible = false
                            detailsProductBinding.forwardPhotoBtn.isVisible = false

                        } else {
                            detailsProductBinding.backPhotoBtn.setOnClickListener {
                                if (indexOfPhoto == 0) {
                                    indexOfPhoto = product!!.photos!!.size - 1
                                } else {
                                    indexOfPhoto--
                                }

                                setPhotoInView(indexOfPhoto)
                            }

                            detailsProductBinding.forwardPhotoBtn.setOnClickListener {
                                if (indexOfPhoto == product!!.photos!!.size - 1) {
                                    indexOfPhoto = 0
                                } else {
                                    indexOfPhoto++
                                }

                                setPhotoInView(indexOfPhoto)
                            }
                        }
                    }
                }
            }catch (e: Exception){
                message.showError("Ошибка загрузки данных о продукции", detailsProductBinding.root)
            }
        }
    }

    private fun setPhotoInView(index: Int){
        val fileWorker = FileWorker()
        detailsProductBinding.photosView.setImageBitmap(fileWorker.getBitmap(product!!.photos!![index]))
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}