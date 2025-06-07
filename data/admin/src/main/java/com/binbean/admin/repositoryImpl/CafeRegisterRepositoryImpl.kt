package com.binbean.admin.repositoryImpl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.binbean.admin.BuildConfig
import com.binbean.admin.api.CafeRegisterService
import com.binbean.admin.datasource.CafeRegisterRemoteDataSource
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.admin.dto.FloorDetail
import com.binbean.admin.dto.FloorWrapper
import com.binbean.admin.dto.Position
import com.binbean.util.uriListToMultipartParts
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import androidx.core.content.edit

class CafeRegisterRepositoryImpl @Inject constructor(
    private val cafeRegisterRemoteDataSource: CafeRegisterRemoteDataSource
) {

    /**
     * 카페를 등록하는 함수
     */
    suspend fun registerCafe(
        context: Context,
        request: CafeRegisterRequest,
        imageUris: List<Uri>,
        floorList: List<FloorWrapper>
    ): Boolean {
        val result =
            cafeRegisterRemoteDataSource.registerCafe(context, request, imageUris, floorList)

        return result.fold(
            onSuccess = { cafeId ->
                context.getSharedPreferences("binbean_prefs", Context.MODE_PRIVATE)
                    .edit {
                        putInt("cafeId", cafeId)
                    }
                Log.d(TAG, "pref 등록 성공: $cafeId")
                true
            },
            onFailure = {
                Log.w(TAG, "pref 등록 실패. ${it.message}")
                false
            }
        )
    }

    companion object {
        private const val TAG = "CafeRegisterRepositoryImpl"
    }
}