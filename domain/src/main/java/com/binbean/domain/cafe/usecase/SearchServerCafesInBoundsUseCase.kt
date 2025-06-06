package com.binbean.domain.cafe.usecase

import com.binbean.domain.cafe.ServerCafe
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class SearchServerCafesInBoundsUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): List<ServerCafe> {
        return cafeRepository.searchServerCafesInBounds(lat, lng)
    }
}