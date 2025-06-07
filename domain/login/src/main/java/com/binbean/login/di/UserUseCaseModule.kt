package com.binbean.login.di

import com.binbean.login.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

//    @Provides
//    fun provideAuthenticateWithKakaoUseCase(
//        authenticationRepository: AuthenticationRepository,
//        kakaoAuthenticationRepository: KakaoAuthenticationRepository
//    ) = AuthenticateWithKakaoUseCase(authenticationRepository, kakaoAuthenticationRepository)
//
//    @Provides
//    fun provideAuthenticateWithServerUseCase(
//        authenticationRepository: AuthenticationRepository,
//        serverAuthenticationRepository: ServerAuthenticationRepository
//    ) = LoginOrRegisterUseCase(authenticationRepository, serverAuthenticationRepository)
}