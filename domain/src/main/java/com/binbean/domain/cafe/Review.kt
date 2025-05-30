package com.binbean.domain.cafe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
    val id: Int = -1,
    @SerializedName("createdAt")
    val date: String = "2025-05-29",
    @SerializedName("reviewer")
    val nickname: String = "홍길동",
    val reviewScore: Double,
    val reviewText: String,
    val isMyReview: Boolean = false,
    @SerializedName("reviewImgUrl")
    val reviewImgUrlList: List<ReviewImage>
): Serializable

data class ReviewImage(
    val url: String
): Serializable