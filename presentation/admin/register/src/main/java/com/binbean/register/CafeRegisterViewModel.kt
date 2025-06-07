package com.binbean.register

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.admin.repositoryImpl.CafeRegisterRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeRegisterViewModel @Inject constructor(
    private val cafeRepository: CafeRegisterRepositoryImpl
) : ViewModel() {
    private val _request = MutableLiveData<CafeRegisterRequest>()
    val request: LiveData<CafeRegisterRequest> = _request
    private val _imageUris = MutableLiveData<List<Uri>>()
    val imageUris: LiveData<List<Uri>> = _imageUris

    /**
     * 카페 기본정보를 세팅하는 함수
     */
    fun setRequest(baseData: CafeRegisterRequest) {
        _request.value = baseData
    }

    /**
     * 카페 등록 사진 세팅하는 함수
     */
    fun setImageUris(photos: List<Uri>) {
        _imageUris.postValue(photos)
    }

    /**
     * 사용자가 설정한 요일들을 매핑하는 함수
     */
    fun setWeekTimes(weekTimes: List<DayTime>) {
        val base = _request.value ?: return
        _request.value = base.copy(
            monday_start = weekTimes[0].start,
            monday_end = weekTimes[0].end,
            tuesday_start = weekTimes[1].start,
            tuesday_end = weekTimes[1].end,
            wednesday_start = weekTimes[2].start,
            wednesday_end = weekTimes[2].end,
            thursday_start = weekTimes[3].start,
            thursday_end = weekTimes[3].end,
            friday_start = weekTimes[4].start,
            friday_end = weekTimes[4].end,
            saturday_start = weekTimes[5].start,
            saturday_end = weekTimes[5].end,
            sunday_start = weekTimes[6].start,
            sunday_end = weekTimes[6].end
        )
    }

    /**
     * 카페 기본정보 리퀘스트 반환 함수
     */
    fun getFinalRequest(): CafeRegisterRequest? = _request.value

    /**
     * 카페 등록 함수
     */
    fun registerCafe(context: Context) {
        viewModelScope.launch {
            val request = request.value ?: return@launch
            val images = imageUris.value.orEmpty()

            if (images.isEmpty()) {
                return@launch
            }

            cafeRepository.registerCafe(context, request, images)
        }
    }
}