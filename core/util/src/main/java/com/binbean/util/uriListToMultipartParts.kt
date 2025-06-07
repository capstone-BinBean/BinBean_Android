package com.binbean.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun uriListToMultipartParts(context: Context, uris: List<Uri>, partName: String): List<MultipartBody.Part> {
    return uris.mapIndexedNotNull { index, uri ->
        uriToMultipartPart(context, uri, partName, index)
    }
}
