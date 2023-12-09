package com.example.publishinghousekotlin.controllers

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import com.example.publishinghousekotlin.basics.FileWorker
import com.example.publishinghousekotlin.databinding.DetailsEmployeeBinding
import com.example.publishinghousekotlin.dialogs.DeleteEmployeeDialog
import com.example.publishinghousekotlin.models.EmployeeDTO
import java.time.format.DateTimeFormatter

class DetailsEmployeeActivity: AppCompatActivity() {

    private lateinit var detailsEmployeeBinding: DetailsEmployeeBinding
    private var employeeDTO: EmployeeDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsEmployeeBinding = DetailsEmployeeBinding.inflate(layoutInflater)
        setContentView(detailsEmployeeBinding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        setStartData()

        detailsEmployeeBinding.deleteBtn.setOnClickListener {
            DeleteEmployeeDialog(employeeDTO!!.id, detailsEmployeeBinding.root).show(supportFragmentManager, "DELETEEMPLOYEEDIALOG")
        }

        detailsEmployeeBinding.editBtn.setOnClickListener {
            val intent = Intent(this@DetailsEmployeeActivity, SaveEmployeeActivity::class.java)
            intent.putExtra("employee", employeeDTO)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setStartData() {
        employeeDTO = intent.getSerializableExtra("employee") as? EmployeeDTO

        val surname = SpannableStringBuilder().bold { append("Фамилия: ") }.append(employeeDTO?.surname)
        val name = SpannableStringBuilder().bold { append("Имя: ") }.append(employeeDTO?.name)

        val patronymicAppend = if(employeeDTO?.patronymic!!.isEmpty()) "отсутствует" else employeeDTO?.patronymic
        val patronymic = SpannableStringBuilder().bold { append("Отчество: ") }.append(patronymicAppend)
        val birthday = SpannableStringBuilder().bold { append("Дата рождения: ") }.append(employeeDTO?.birthday!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        val post = SpannableStringBuilder().bold { append("Должность: ") }.append(employeeDTO?.post)
        val email = SpannableStringBuilder().bold { append("Электронная почта: ") }.append(employeeDTO?.email)
        val phone = SpannableStringBuilder().bold { append("Номер телефона: ") }.append(employeeDTO?.phone)

        detailsEmployeeBinding.surnameTextView.text = surname
        detailsEmployeeBinding.nameTextView.text = name
        detailsEmployeeBinding.patronymicTextView.text = patronymic
        detailsEmployeeBinding.birthdayTextView.text = birthday
        detailsEmployeeBinding.postTextView.text = post
        detailsEmployeeBinding.emailTextView.text = email
        detailsEmployeeBinding.phoneTextView.text = phone
        detailsEmployeeBinding.photoView.setImageBitmap(FileWorker().getBitmap(employeeDTO!!.photo))


    }


}