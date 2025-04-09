package com.binbean.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binbean.domain.cafe.Cafe

class CafeBottomSheetViewModel : ViewModel() {
    private val _cafe = MutableLiveData<Cafe>()
    val cafe: LiveData<Cafe> get() = _cafe

    fun setCafe(cafe: Cafe) {
        _cafe.value = cafe
    }
}