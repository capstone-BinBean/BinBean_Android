package com.binbean.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binbean.admin.dto.CafeImageUrl
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.admin.dto.Floor

class CafeRegisterViewModel : ViewModel() {
    private val _request = MutableLiveData<CafeRegisterRequest?>()
    val request: LiveData<CafeRegisterRequest?> = _request

    fun initRequest(baseData: CafeRegisterRequest) {
        _request.value = baseData
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
}