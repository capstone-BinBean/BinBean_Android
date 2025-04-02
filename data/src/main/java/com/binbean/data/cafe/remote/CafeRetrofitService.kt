package com.binbean.data.cafe.remote

import com.binbean.data.cafe.KakaoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CafeRetrofitService {
    @GET("/v2/local/search/keyword")
    suspend fun searchKeyword(
        @Query("query") query: String,
        @Query("rect") rect: String
    ): Response<KakaoSearchResponse>
}