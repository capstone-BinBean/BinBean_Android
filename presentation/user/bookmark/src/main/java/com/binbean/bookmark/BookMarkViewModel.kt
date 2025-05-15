package com.binbean.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CongestionStatus
import com.binbean.domain.cafe.Seat

class BookMarkViewModel: ViewModel() {
    private val _bookmarkedCafes = MutableLiveData<List<Cafe>>()
    val bookmarkedCafes: LiveData<List<Cafe>> = _bookmarkedCafes

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        _bookmarkedCafes.value = listOf(
            Cafe(
                id = "1",
                name = "컴포즈커피 춘천점",
                address = "강원도 춘천시 중앙로 1-16 1층",
                status = CongestionStatus.FREE,
                seats = listOf(
                    Seat(35, true, true),
                    Seat(25, false, false),
                    Seat(20, true, false),
                    Seat(21, false, false),
                    Seat(3, false, false),
                    Seat(4, false, false),
                    Seat(11, false, false),
                    Seat(23, false, false)
                )
            ),
            Cafe(
                id = "2",
                name = "스타벅스 춘천 퇴계 DT점",
                address = "춘천시 연미로 95",
                status = CongestionStatus.BUSY,
                seats = listOf(
                    Seat(41, true, false),
                    Seat(7, false, true)
                )
            ),
            Cafe(
                id = "3",
                name = "커피통 우두점",
                address = "춘천시 사우로 32",
                status = CongestionStatus.NORMAL,
                seats = listOf(
                    Seat(1, true, false),
                    Seat(2, true, true),
                    Seat(3, true, true)
                )
            )
        )
    }
}