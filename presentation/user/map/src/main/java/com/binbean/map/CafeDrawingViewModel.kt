package com.binbean.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.FloorPlanResponse
import com.binbean.domain.cafe.usecase.GetCafeDetailUseCase
import com.binbean.domain.cafe.usecase.GetFloorPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeDrawingViewModel @Inject constructor(
    private val getCafeDetailUseCase: GetCafeDetailUseCase,
    private val getFloorPlanUseCase: GetFloorPlanUseCase
) : ViewModel() {

    private val _cafeDetail = MutableLiveData<CafeDetail>()
    val cafeDetail: LiveData<CafeDetail> = _cafeDetail

    private val _floorPlans = MutableLiveData<List<FloorPlanResponse>>()
    val floorPlans: LiveData<List<FloorPlanResponse>> = _floorPlans

    fun loadCafeDetail(cafeId: Int) {
        viewModelScope.launch {
            try {
                val detail = getCafeDetailUseCase(cafeId)
                _cafeDetail.value = detail
            } catch (e: Exception) {
                Log.e("CafeDrawingVM", "카페 상세 조회 실패", e)
            }
        }
    }

    fun loadFloorPlan(floorPlanId: Int) {
        viewModelScope.launch {
            try {
                val plans = getFloorPlanUseCase(floorPlanId)
                _floorPlans.value = plans
            } catch (e: Exception) {
                Log.e("CafeDrawingVM", "도면 조회 실패", e)
            }
        }
    }
}