package com.binbean.data.cafe.repositoryImpl

import com.binbean.data.cafe.remote.CafeRetrofitService
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val cafeRetrofitService: CafeRetrofitService
): CafeRepository {
    override suspend fun searchCafesInBounds() {
        TODO("Not yet implemented")
    }
}