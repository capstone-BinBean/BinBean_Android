package com.binbean.domain.cafe

import java.io.Serializable

data class ReviewPostRequest(
    val reviewScore: Double,
    val reviewText: String,
    val reviewImgUrlList: List<ReviewImage>
): Serializable
