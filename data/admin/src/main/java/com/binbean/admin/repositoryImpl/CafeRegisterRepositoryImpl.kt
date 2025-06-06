package com.binbean.admin.repositoryImpl

import com.binbean.admin.api.CafeRegisterService
import com.binbean.admin.di.AdminRetrofitModule
import com.binbean.retrofit.BuildConfig
import javax.inject.Inject

class CafeRegisterRepositoryImpl @Inject constructor(
    private val adminRetrofitModule: AdminRetrofitModule,
    private val cafeRegisterService: CafeRegisterService
) {
    private val token = "Bearer ${BuildConfig.A}"
}