package com.carlostorres.showofftest.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.carlostorres.client.data.InstagramRepository
import com.carlostorres.client.data.remote.InstagramService
import com.carlostorres.client.data.remote.createService
import com.carlostorres.client.domain.repository.Repository
import com.carlostorres.showofftest.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(val context: Context) {

    @Provides
    @Singleton
    fun provideRepositorio(
        service: InstagramService,
        sharedPreferences: SharedPreferences
    ): Repository = InstagramRepository(service, sharedPreferences)

    @Provides
    @Singleton
    fun providesService(): InstagramService = createService(
	    context,
	    InstagramService::class.java,
        BuildConfig.API,
	    context.getSharedPreferences("prefs", MODE_PRIVATE)
	)

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences = context.getSharedPreferences("prefs", MODE_PRIVATE)
}