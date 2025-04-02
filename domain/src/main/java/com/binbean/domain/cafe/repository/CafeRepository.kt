package com.binbean.domain.cafe.repository

interface CafeRepository {
    suspend fun searchCafesInBounds()
}