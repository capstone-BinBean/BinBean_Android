package com.binbean.domain.cafe.repository

import com.binbean.domain.FavoriteCafeResponse
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.ServerCafe

interface CafeRepository {
    suspend fun searchCafesInBounds(
        latitude: Double,
        longitude: Double
    ): List<Cafe>

    suspend fun searchServerCafesInBounds(
        latitude: Double,
        longitude: Double
    ): List<ServerCafe>

    suspend fun searchCafesByKeyword(
        keyword: String
    ): List<Cafe>

    suspend fun getCafeDetail(
        cafeId: Int
    ): CafeDetail

    suspend fun getFavoriteCafes(

    ): List<FavoriteCafeResponse>
}