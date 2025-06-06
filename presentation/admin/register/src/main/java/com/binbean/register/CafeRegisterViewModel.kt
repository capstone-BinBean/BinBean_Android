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

    fun initRequest(baseData: CafeRegisterRequest) {
        _request.value = baseData
    }

    fun setImageUris(photos: List<Uri>) {
        _imageUris.postValue(photos)
    }

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

    fun getFinalRequest(): CafeRegisterRequest? = _request.value

    fun registerCafe(context: Context) {
        viewModelScope.launch {
            request.value?.let { cafeRepository.registerCafe(context, it, imageUris.value!!) }
        }
    }
}