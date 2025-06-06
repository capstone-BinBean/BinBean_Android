package com.binbean.login.di

import com.binbean.login.api.ServerAuthRetrofitService
import com.binbean.retrofit.RetrofitModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideServerAuthRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit): ServerAuthRetrofitService {
        return retrofit.create(ServerAuthRetrofitService::class.java)
    }
}