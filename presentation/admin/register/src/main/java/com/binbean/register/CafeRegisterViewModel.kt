package com.binbean.register

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.admin.dto.FloorWrapper
import com.binbean.admin.model.DayTime
import com.binbean.admin.model.PhotoItem
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
    private val _imageItems = MutableLiveData<List<PhotoItem>>()
    val imageItems: LiveData<List<PhotoItem>> = this._imageItems
    private val _floorList = MutableLiveData<List<FloorWrapper>>()
    val floorList: LiveData<List<FloorWrapper>> = _floorList

    fun setLocalImages(uris: List<Uri>) {
        val current = this._imageItems.value.orEmpty().filterIsInstance<PhotoItem.Remote>()
        this._imageItems.value = current + uris.map { PhotoItem.Local(it) }
    }

    fun setRemoteImages(urls: List<String>) {
        val current = this._imageItems.value.orEmpty().filterIsInstance<PhotoItem.Local>()
        this._imageItems.value = urls.map { PhotoItem.Remote(it) } + current
    }


    /**
     * 카페 기본정보를 세팅하는 함수
     */
    fun setRequest(baseData: CafeRegisterRequest) {
        _request.value = baseData
    }

    /**
     * 카페 등록 사진 세팅하는 함수
     */
    fun setImageList(photos: List<PhotoItem>) {
        _imageItems.value = photos
    }

    /**
     * 사용자가 설정한 요일들을 매핑하는 함수
     */
    fun setWeekTimes(weekTimes: List<DayTime>) {
        val base = _request.value ?: return
        _request.value = base.copy(
            mondayStart = weekTimes[0].start,
            mondayEnd = weekTimes[0].end,
            tuesdayStart = weekTimes[1].start,
            tuesdayEnd = weekTimes[1].end,
            wednesdayStart = weekTimes[2].start,
            wednesdayEnd = weekTimes[2].end,
            thursdayStart = weekTimes[3].start,
            thursdayEnd = weekTimes[3].end,
            fridayStart = weekTimes[4].start,
            fridayEnd = weekTimes[4].end,
            saturdayStart = weekTimes[5].start,
            saturdayEnd = weekTimes[5].end,
            sundayStart = weekTimes[6].start,
            sundayEnd = weekTimes[6].end
        )
    }

    /**
     * 카페 도면 정보 세팅하는 함수
     */
    fun setFloorList(floors: List<FloorWrapper>) {
        _floorList.value = floors
    }

    /**
     * 카페 기본정보 리퀘스트 반환 함수
     */
    fun getFinalRequest(): CafeRegisterRequest? = _request.value

    /**
     * 카페 등록 함수
     */
    suspend fun registerCafe(context: Context): Int? {
        val request = _request.value
        val images = _imageItems.value.orEmpty().filterIsInstance<PhotoItem.Local>().map { it.uri }
        val floors = _floorList.value.orEmpty()

        return cafeRepository.registerCafe(context, request, images, floors)
    }
}