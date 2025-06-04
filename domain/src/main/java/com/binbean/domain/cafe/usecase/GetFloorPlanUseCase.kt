package com.binbean.domain.cafe.usecase

import com.binbean.domain.cafe.FloorPlanResponse
import com.binbean.domain.cafe.repository.CafeRepository
import javax.inject.Inject

class GetFloorPlanUseCase @Inject constructor(
    private val repository: CafeRepository
) {
    suspend operator fun invoke(floorPlanId: Int): List<FloorPlanResponse> {
        return repository.getFloorPlan(floorPlanId)
    }
}