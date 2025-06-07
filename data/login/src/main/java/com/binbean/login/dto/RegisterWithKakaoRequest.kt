package com.binbean.login.dto

import com.google.gson.annotations.SerializedName

data class RegisterWithKakaoRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("role")
    val role: String
)