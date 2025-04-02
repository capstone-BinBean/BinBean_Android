package com.binbean.data.cafe.di

import com.binbean.data.cafe.repositoryImpl.CafeRepositoryImpl
import com.binbean.domain.cafe.repository.CafeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CafeRepositoryModule {
    @Binds
    abstract fun bindCafeRepository(
        impl: CafeRepositoryImpl
    ): CafeRepository
}