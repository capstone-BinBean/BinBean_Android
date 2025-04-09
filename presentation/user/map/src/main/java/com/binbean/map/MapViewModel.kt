package com.binbean.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.SearchCafesInBoundsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val searchCafesInBoundsUseCase: SearchCafesInBoundsUseCase
): ViewModel(){

    private val _cafeList = MutableLiveData<List<Cafe>>()
    val cafeList: LiveData<List<Cafe>> = _cafeList

    private val _selectedCafe = MutableLiveData<Cafe?>()
    val selectedCafe: LiveData<Cafe?> = _selectedCafe

    fun loadCafes(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val cafes = searchCafesInBoundsUseCase(lat, lng)
                _cafeList.value = cafes
                Log.d("MapViewModel", "불러온 카페 리스트: $cafes")
            } catch (e: Exception) {
                Log.e("MapViewModel", "카페 불러오기 실패", e)
            }
        }
    }

    fun selectCafe(cafe: Cafe) {
        _selectedCafe.value = cafe
        Log.d("MapViewModel", "선택된 카페: $cafe")
    }

    fun clearSelectedCafe() {
        _selectedCafe.value = null
    }
}