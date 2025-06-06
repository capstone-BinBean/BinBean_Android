package com.binbean.user.di

import com.binbean.retrofit.RetrofitModule
import com.binbean.user.api.DetectService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DetectRetrofitModule {

    @Provides
    fun provideDetectRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): DetectService {
        return retrofit.create(DetectService::class.java)
    }
}