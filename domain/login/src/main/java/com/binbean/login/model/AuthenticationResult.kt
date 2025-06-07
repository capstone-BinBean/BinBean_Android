package com.binbean.login.model

sealed class AuthenticationResult {
    data object NotLoaded : AuthenticationResult()
    data object NoToken : AuthenticationResult()
    data object RefreshTokenExpired : AuthenticationResult()

    data class AuthenticationSuccess(val authData: KakaoAuthData) : AuthenticationResult()
    data class AuthenticationError(val message: String) : AuthenticationResult()
}