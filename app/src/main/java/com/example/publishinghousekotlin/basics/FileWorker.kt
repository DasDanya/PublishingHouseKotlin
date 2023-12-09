package com.example.publishinghousekotlin.basics


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Base64


class FileWorker() {

     fun uriToFile(uri: Uri, context: Context): File? {

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
        val filePath: String? = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        filePath?.let { return File(it) }
        return null
    }

    fun getBitmap(photo: String): Bitmap{
        val bytes = Base64.getDecoder().decode(photo)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun fromBase64ToFile(base64String: String): File{
        val decodedBytes = Base64.getDecoder().decode(base64String)
        val byteArrayInputStream = ByteArrayInputStream(decodedBytes)

        val tempFile = File.createTempFile("photo", ".png")
        val fileOutputStream = FileOutputStream(tempFile)

        fileOutputStream.write(byteArrayInputStream.readBytes())
        fileOutputStream.close()
        byteArrayInputStream.close()

        return tempFile
    }

}