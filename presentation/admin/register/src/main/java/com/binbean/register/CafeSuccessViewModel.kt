package com.binbean.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.CafeInfoImgItem
import com.binbean.domain.cafe.usecase.GetCafeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeSuccessViewModel @Inject constructor(
    private val getCafeDetailUseCase: GetCafeDetailUseCase
) : ViewModel() {

    private val _cafeDetail = MutableLiveData<CafeDetail>()
    val cafeDetail: LiveData<CafeDetail> = _cafeDetail

    private val _photoList = MutableLiveData<List<CafeInfoImgItem>>()
    val photoList: LiveData<List<CafeInfoImgItem>> = _photoList

    fun loadCafeDetail(cafeId: Int) {
        viewModelScope.launch {
            try {
                val result = getCafeDetailUseCase(cafeId)
                _cafeDetail.value = result
            } catch (e: Exception) {
                Log.e("CafeSuccessVM", "카페 상세 불러오기 실패", e)
            }
        }
    }
}