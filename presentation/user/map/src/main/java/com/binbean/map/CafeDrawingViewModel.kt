package com.binbean.map

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbean.user.dto.DetectRequest
import com.binbean.user.dto.Position
import com.binbean.user.repositoryImpl.DetectRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeDrawingViewModel @Inject constructor(
    private val detectRepository: DetectRepositoryImpl
) : ViewModel() {

    // 더미 리퀘스트
    private val request = DetectRequest(
        borderPosition = listOf(Position(0, 0), Position(10, 0)),
        seatPosition = listOf(
            Position(3, 123), Position(65, 28), Position(92, 180),
            Position(152, 101), Position(134, 216), Position(189, 138),
            Position(242, 289), Position(304, 213), Position(342, 462)
        ),
        doorPosition = listOf(Position(0, 5)),
        counterPosition = listOf(Position(5, 1)),
        toiletPosition = listOf(Position(8, 6)),
        windowPosition = listOf(Position(1, 0))
    )
    // 더미 플로어 넘버
    private val floorNumber = 2

    fun detect(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            detectRepository.detect(context, floorNumber, request, imageUri)
        }
    }
}