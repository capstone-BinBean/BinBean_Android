package com.binbean.user.api

import com.binbean.user.dto.DetectResponse
import com.binbean.user.dto.FloorList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface DetectService {

    @Multipart
    @POST("/api/cctv/detect")
    suspend fun detect(
        @Header("Authorization") token: String,
        @Part("floorNumber") floorNumber: RequestBody,
        @Part("floorList") floorList: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<DetectResponse>
}