package com.binbean.login.dto

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("authType")
    val authType: String,
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)