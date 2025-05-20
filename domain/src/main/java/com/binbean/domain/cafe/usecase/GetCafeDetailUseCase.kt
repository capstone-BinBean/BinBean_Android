package com.binbean.domain.cafe.usecase

import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class GetCafeDetailUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(cafeId: Int): CafeDetail {
        return cafeRepository.getCafeDetail(cafeId)
    }
}