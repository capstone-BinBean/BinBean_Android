package com.binbean.domain.cafe.usecase

import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class SearchCafeKeywordsUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(keyword: String): List<Cafe> {
        return cafeRepository.searchCafesByKeyword(keyword)
    }
}