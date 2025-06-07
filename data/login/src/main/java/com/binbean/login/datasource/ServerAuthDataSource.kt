package com.binbean.login.datasource

import com.binbean.login.api.ServerAuthRetrofitService
import javax.inject.Inject

class ServerAuthDataSource @Inject constructor(
    private val authApi: ServerAuthRetrofitService
) {

//    suspend fun register(
//        email: String,
//        nickname: String,
//        profile: String,
//        role: String
//    ): Result<TokenData> = try {
//        val response = authApi.registerWithKakao(
//            RegisterWithKakaoRequest(
//                email = email,
//                nickname = nickname,
//                profile = profile,
//                role = role
//            )
//        )
//        handleRegisterResponse(response)
//    } catch (e: Exception) {
//        Result.failure(e)
//    }

//    // 회원가입 시 Response를 처리
//    private fun handleRegisterResponse(response: Response<AuthResponse>): Result<TokenData> = when {
//        response.isSuccessful && response.body()?.data != null -> {
//            Result.success(response.body()!!.data)
//        }
//
//        response.code() == 400 -> {
//            Result.failure(IllegalStateException(ALREADY_REGISTERED))
//        }
//
//        else -> {
//            Result.failure(
//                Exception("Unexpected error: ${response.code()} - ${response.message()}")
//            )
//        }
//    }

    companion object {
        private const val ALREADY_REGISTERED = "해당 사용자는 이미 회원가입됨."
    }
}