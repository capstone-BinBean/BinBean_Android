package com.binbean.login.repository

import com.binbean.login.model.KakaoAuthData
import com.binbean.login.model.ServerAuthData

interface AuthenticationRepository {
    fun getKakaoAuthData(): KakaoAuthData

    fun getServerAuthData(): ServerAuthData
}