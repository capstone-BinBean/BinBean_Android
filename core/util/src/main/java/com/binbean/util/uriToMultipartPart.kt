package com.binbean.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun uriToMultipartPart(context: Context, uri: Uri, partName: String, index: Int): MultipartBody.Part? {
    return runCatching {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File.createTempFile("upload_${index}_", ".jpg", context.cacheDir).apply {
            outputStream().use { inputStream.copyTo(it) }
        }
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }.getOrElse {
        it.printStackTrace()
        null
    }
}
