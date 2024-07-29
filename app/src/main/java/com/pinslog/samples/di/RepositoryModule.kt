package com.pinslog.samples.di

import com.pinslog.samples.data.FakeDataRepo
import com.pinslog.samples.data.FakeDataRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFakeRepository(): FakeDataRepo = FakeDataRepoImpl()
}