package com.binbean.login.api

import com.binbean.login.dto.RegisterWithKakaoRequest
import com.binbean.login.dto.ServerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerAuthRetrofitService {
    @POST("/api/auths/registration")
    suspend fun registerWithKakao(@Body request: RegisterWithKakaoRequest): Response<ServerResponse>
}