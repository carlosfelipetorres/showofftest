package com.carlostorres.showofftest.di.modules

import com.carlostorres.client.domain.interactors.*
import com.carlostorres.client.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideGetParameterInteractor(repository: Repository): GetParameter = GetParameter(repository)

    @Provides
    @Singleton
    fun provideSaveParameterInteractor(repository: Repository): SaveParameter = SaveParameter(repository)

    @Provides
    @Singleton
    fun provideClearParamsInteractor(repository: Repository): CleanParameters = CleanParameters(repository)

    @Provides
    @Singleton
    fun provideProfileInteractor(repository: Repository): Profile = Profile(repository)

    @Provides
    @Singleton
    fun providePostsInteractor(repository: Repository): Posts = Posts(repository)
}