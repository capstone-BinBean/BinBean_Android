package com.binbean.admin.api

import com.binbean.admin.dto.CafeRegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CafeRegisterService {

    @POST("/api/cafes/registration")
    suspend fun registerCafe(
        @Body request: CafeRegisterRequest
    ): Response<ResponseBody>
}