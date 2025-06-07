package com.binbean.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val authenticateWithKakaoUseCase: AuthenticateWithKakaoUseCase,
//    private val authenticateWithServerUseCase: AuthenticateWithServerUseCase,
//    private val getUserInfoUseCase: GetUserInfoUseCase,
//    private val loginOrRegisterUseCase: LoginOrRegisterUseCase,
//    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

//    private val kakaoAuthStatus by lazy {
//        authenticateWithKakaoUseCase().stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = AuthenticationResult.NotLoaded
//        )
//    }
//
//    private val _appLoginStatus = MutableStateFlow<AppLoginState>(AppLoginState.TryAutoLogin)
//    val appLoginStatus = _appLoginStatus.asStateFlow()
//
//    fun clearAppLoginStatus(state: AppLoginState) {
//        _appLoginStatus.value = state
//    }
//
//    fun checkKakaoTokenExist() {
//        viewModelScope.launch(ioDispatcher) {
//            kakaoAuthStatus.collect { result ->
//                when (result) {
//                    is AuthenticationResult.NoToken,
//                    is AuthenticationResult.RefreshTokenExpired -> {
//                        _appLoginStatus.emit(AppLoginState.Idle.NotKakaoLoggedIn)
//                    }
//
//                    is AuthenticationResult.AuthenticationError -> {
//                        _appLoginStatus.emit(AppLoginState.KakaoLoginFailed(result.message))
//                    }
//
//                    is AuthenticationResult.AuthenticationSuccess -> {
//                        checkServerTokenExist()
//                    }
//
//                    is AuthenticationResult.NotLoaded -> Unit
//                }
//            }
//        }
//    }
//
//    private fun mapOAuthTokenToKakaoAuthData(token: OAuthToken) = KakaoAuthData(
//        kakaoAccessToken = token.accessToken,
//        kakaoRefreshToken = token.refreshToken,
//        accessTokenExpirationTime = token.accessTokenExpiresAt.time
//    )
//
//    fun onKakaoAuthorizationFailure(error: Throwable?) {
//        viewModelScope.launch(ioDispatcher) {
//            error?.let {
//                when {
//                    error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
//                        _appLoginStatus.emit(AppLoginState.Idle.NotKakaoLoggedIn)
//                    }
//
//                    error is AuthError && error.reason == AuthErrorCause.AccessDenied -> {
//                        _appLoginStatus.emit(AppLoginState.Idle.NotKakaoLoggedIn)
//                    }
//
//                    else -> {
//                        Log.e(TAG, "카카오 로그인 실패", error)
//                        _appLoginStatus.emit(AppLoginState.KakaoLoginFailed(error.message ?: ERROR))
//                    }
//                }
//            }
//        }
//    }

//    fun onKakaoAuthorizationSuccess(token: OAuthToken?) {
//        token?.let {
//            viewModelScope.launch(ioDispatcher) {
//                authenticateWithKakaoUseCase(mapOAuthTokenToKakaoAuthData(token))
//            }
//            loginWithServer()
//        } ?: run {
//            _appLoginStatus.value = AppLoginState.KakaoLoginFailed(ERROR)
//        }
//    }

//    private fun checkServerTokenExist() {
//        viewModelScope.launch(ioDispatcher) {
//            val serverAuthData = authenticateWithServerUseCase()
//            if (serverAuthData.accessToken.isBlank() || serverAuthData.refreshToken.isBlank()) {
//                _appLoginStatus.emit(AppLoginState.Idle.NotServerLoggedIn)
//            } else {
//                _appLoginStatus.emit(AppLoginState.LoginComplete)
//            }
//        }
//    }
//
//    fun loginWithServer() {
//        viewModelScope.launch(ioDispatcher) {
//            getUserInfoUseCase()?.let {
//                try {
//                    loginOrRegisterUseCase(it.serviceId, it.name, it.email, it.profileUrl)
//                    _appLoginStatus.emit(AppLoginState.LoginComplete)
//                } catch (e: Exception) {
//                    Log.e(TAG, "서버 로그인 실패", e)
//                    _appLoginStatus.emit(AppLoginState.ServerLoginFailed(e.message ?: ERROR))
//                }
//            } ?: run {
//                _appLoginStatus.emit(AppLoginState.ServerLoginFailed("유저 정보를 가져올 수 없습니다"))
//            }
//        }
//    }

    companion object {
        private const val TAG = "LoginViewModel"
        private const val ERROR = "알 수 없는 오류가 발생했습니다"
    }
}
