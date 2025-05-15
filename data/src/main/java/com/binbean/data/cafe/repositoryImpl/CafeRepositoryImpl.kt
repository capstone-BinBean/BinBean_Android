package com.binbean.data.cafe.repositoryImpl

import com.binbean.data.cafe.remote.CafeRetrofitService
import com.binbean.data.cafe.toCafe
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val cafeRetrofitService: CafeRetrofitService
): CafeRepository {
    override suspend fun searchCafesInBounds(
        latitude: Double,
        longitude: Double
    ): List<Cafe> {
        val delta = 0.01 // 예시: 1km 정도 반경
        val rect = "${longitude - delta},${latitude - delta},${longitude + delta},${latitude + delta}"

        val response = cafeRetrofitService.searchKeyword("카페", rect)
        if (response.isSuccessful) {
            return response.body()?.documents?.map {
                it.toCafe()
            } ?: emptyList()
        } else {
            throw Exception("API 오류")
        }
    }

    override suspend fun searchCafesByKeyword(keyword: String): List<Cafe> {
        val response = cafeRetrofitService.searchKeyword(keyword, "")
        if (response.isSuccessful) {
            return response.body()?.documents?.map {
                it.toCafe()
            } ?: emptyList()
        } else {
            throw Exception("API 오류")
        }
    }
}