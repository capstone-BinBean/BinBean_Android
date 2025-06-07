package com.binbean.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ServiceApi

    @Provides
    @Singleton
    fun provideDefaultOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    @KakaoApi
    fun provideKakaoOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @ServiceApi
    fun provideServiceOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃 (기본 10초)
            .readTimeout(30, TimeUnit.SECONDS)    // 응답 타임아웃 (기본 10초)
            .writeTimeout(30, TimeUnit.SECONDS)   // 업로드 타임아웃 (파일 전송에 중요)build()
            .build()
    }

    @Provides
    @Singleton
    @KakaoApi
    fun provideKakaoApiRetrofitBuilder(@KakaoApi client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @ServiceApi
    fun provideServiceApiRetrofit(@ServiceApi client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}