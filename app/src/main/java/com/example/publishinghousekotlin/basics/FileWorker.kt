package com.example.publishinghousekotlin.basics


import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File


class FileWorker(private val context: Context) {

     fun uriToFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
        val filePath: String? = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        filePath?.let { return File(it) }
        return null
    }

}