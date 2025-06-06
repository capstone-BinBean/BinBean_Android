package com.binbean.domain.cafe.usecase

import com.binbean.domain.FavoriteCafeResponse
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class GetBookmarkedCafesUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(): List<FavoriteCafeResponse> {
        return cafeRepository.getFavoriteCafes()
    }

}