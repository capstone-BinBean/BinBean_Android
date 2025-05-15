package com.binbean.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.SearchCafeKeywordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeSearchViewModel @Inject constructor(
    private val searchCafeKeywordsUseCase: SearchCafeKeywordsUseCase
): ViewModel() {
    private val _searchResult = MutableLiveData<List<Cafe>>()
    val searchResult: LiveData<List<Cafe>> = _searchResult

    fun searchCafeKeyword(query: String) {
        viewModelScope.launch {
            try {
                val result = searchCafeKeywordsUseCase(query)
                _searchResult.value = result
            } catch (e: Exception) {
                _searchResult.value = emptyList()
            }
        }
    }
}