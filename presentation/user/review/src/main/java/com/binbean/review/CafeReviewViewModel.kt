package com.binbean.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binbean.domain.Review

class CafeReviewViewModel : ViewModel() {
    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> = _reviewList

    fun loadDummyReviews() {
        _reviewList.value = listOf(
            Review(1, "2025.02.21", "홍길동", 4, "Lorem Ipsum is simply dummy text...", true),
            Review(2, "2025.02.21", "심청이", 3, "Lorem Ipsum is simply dummy text...", false)
        )
    }
}