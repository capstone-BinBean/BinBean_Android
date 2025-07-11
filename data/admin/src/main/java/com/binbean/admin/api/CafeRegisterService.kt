package com.binbean.admin.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeRegisterService {

    @Multipart
    @POST("/api/cafes/registration")
    suspend fun registerCafe(
        @Header("Authorization") token: String,
        @Part("cafe") cafe: RequestBody,
        @Part("floorPlan") floorPlan: RequestBody,
        @Part cafeImg: List<MultipartBody.Part>
    ): Response<ResponseBody>
}