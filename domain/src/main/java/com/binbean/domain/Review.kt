package com.binbean.domain

import java.io.Serializable

data class Review(
    val id: Int = -1,
    val date: String = "2025-05-29",
    val nickname: String = "홍길동",
    val reviewScore: Double,
    val reviewText: String,
    val isMyReview: Boolean = false,
    val reviewImgUrlList: List<ReviewImage>
): Serializable

data class ReviewImage(
    val url: String
): Serializable