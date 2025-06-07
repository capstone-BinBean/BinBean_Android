package com.binbean.admin.di

import com.binbean.admin.api.CafeRegisterService
import com.binbean.retrofit.RetrofitModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AdminRetrofitModule {

    @Provides
    fun provideAdminRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): CafeRegisterService {
        return retrofit.create(CafeRegisterService::class.java)
    }
}