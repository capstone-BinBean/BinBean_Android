package com.binbean.data.cafe.di

import com.binbean.data.cafe.remote.CafeRetrofitServerService
import com.binbean.data.cafe.remote.CafeRetrofitService
import com.binbean.retrofit.RetrofitModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CafeRetrofitModule {
    @Provides
    @Singleton
    fun provideKakaoApiService(@RetrofitModule.KakaoApi retrofit: Retrofit): CafeRetrofitService {
        return retrofit.create(CafeRetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideServiceApiService(@RetrofitModule.ServiceApi retrofit: Retrofit): CafeRetrofitServerService {
        return retrofit.create(CafeRetrofitServerService::class.java)
    }
}