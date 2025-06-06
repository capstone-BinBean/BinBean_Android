package com.binbean.login.model

data class KakaoAuthData(
    val kakaoAccessToken: String,
    val kakaoRefreshToken: String,
    val accessTokenExpirationTime: Long
)
