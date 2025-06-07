package com.binbean.login.state

sealed interface AppLoginState {
    data object TryAutoLogin : AppLoginState

    sealed interface Idle : AppLoginState {
        object NotKakaoLoggedIn : Idle
        object NotServerLoggedIn : Idle
    }

    data class KakaoLoginFailed(val message: String) : AppLoginState
    data class ServerLoginFailed(val message: String) : AppLoginState
    
    data object LoginComplete : AppLoginState
}