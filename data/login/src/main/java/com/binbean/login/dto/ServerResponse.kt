package com.binbean.login.dto

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)