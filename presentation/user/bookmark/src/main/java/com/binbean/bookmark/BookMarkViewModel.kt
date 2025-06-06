package com.binbean.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.domain.FavoriteCafeResponse
import com.binbean.domain.cafe.usecase.GetBookmarkedCafesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val getBookmarkedCafesUseCase: GetBookmarkedCafesUseCase,
): ViewModel() {
    private val _bookmarkedCafes = MutableLiveData<List<FavoriteCafeResponse>>()
    val bookmarkedCafes: LiveData<List<FavoriteCafeResponse>> = _bookmarkedCafes

    init {
        fetchBookmarkedCafes()
    }

    private fun fetchBookmarkedCafes() {
        viewModelScope.launch {
            try {
                val response = getBookmarkedCafesUseCase()
                _bookmarkedCafes.value = response
            } catch (e: Exception) {
                Log.e("BookMarkViewModel", "Failed to load favorites", e)
                _bookmarkedCafes.value = emptyList()
            }
        }
    }
}