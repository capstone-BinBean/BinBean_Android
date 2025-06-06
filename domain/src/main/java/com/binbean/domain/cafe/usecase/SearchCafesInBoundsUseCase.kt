package com.binbean.domain.cafe.usecase

import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class SearchCafesInBoundsUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): List<Cafe> {
        return cafeRepository.searchCafesInBounds(lat, lng)
    }
}