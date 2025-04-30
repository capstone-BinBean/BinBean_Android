package com.binbean.domain.cafe.repository

import com.binbean.domain.cafe.Cafe

interface CafeRepository {
    suspend fun searchCafesInBounds(
        latitude: Double,
        longitude: Double
    ): List<Cafe>
}