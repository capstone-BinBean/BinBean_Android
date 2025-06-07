package com.binbean.login.usecase

import com.binbean.login.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticateWithServerUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke() = authenticationRepository.getServerAuthData()
}