package com.binbean.binbean_android

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BinbeanApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 카카오 키해시 출력
        Log.d("kakaoKeyHash", Utility.getKeyHash(this))

        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY);
    }
}