package com.binbean.admin.repositoryImpl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.binbean.admin.BuildConfig
import com.binbean.admin.api.CafeRegisterService
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.admin.dto.FloorDetail
import com.binbean.admin.dto.FloorWrapper
import com.binbean.admin.dto.Position
import com.binbean.util.uriListToMultipartParts
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CafeRegisterRepositoryImpl @Inject constructor(
    private val cafeRegisterService: CafeRegisterService
) {
    private val token = "Bearer ${BuildConfig.ADMIN_API_TOKEN}"

    val floorList: List<FloorWrapper> = listOf(
        FloorWrapper(
            floorList = FloorDetail(
                borderPosition = listOf(Position(0, 0), Position(10, 0)),
                seatPosition = listOf(Position(2, 3), Position(3, 3)),
                doorPosition = listOf(Position(0, 5)),
                counterPosition = listOf(Position(5, 1)),
                toiletPosition = listOf(Position(8, 6)),
                windowPosition = listOf(Position(1, 0))
            ),
            floorNumber = 1,
            maxSeats = 20
        )
    )

    /**
     * 카페를 등록하는 함수
     */
    suspend fun registerCafe(context: Context, request: CafeRegisterRequest, imageUris: List<Uri>) {
        val gson = Gson()
        val jsonCafe = gson.toJson(request)
            .toRequestBody("application/json".toMediaTypeOrNull())
        val jsonFloor = gson.toJson(floorList)
            .toRequestBody("application/json".toMediaTypeOrNull())
        val imageParts = uriListToMultipartParts(context, imageUris, "cafeImg")

        Log.d(TAG, "이미지 파트 수: ${imageParts.size}")
        try {
            val response = cafeRegisterService.registerCafe(
                token = token,
                cafe = jsonCafe,
                floorPlan = jsonFloor,
                cafeImg = imageParts
            )
            if (response.isSuccessful) {
                Log.d(
                    TAG,
                    "등록 성공: HTTP ${response.code()} - ${response.body()?.string() ?: "No body"}"
                )
            } else {
                Log.e(TAG, "등록 실패: HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "예외 발생: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "CafeRegisterRepositoryImpl"
    }
}