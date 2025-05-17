package com.binbean.domain

data class Review(
    val id: Int,
    val date: String,
    val nickname: String,
    val rating: Int, // 1~5
    val content: String,
    val isMyReview: Boolean
)