package com.binbean.domain.cafe

import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class SearchCafeKeywordsUseCase @Inject constructor(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(keyword: String): List<Cafe> {
        return cafeRepository.searchCafesByKeyword(keyword)
    }
}