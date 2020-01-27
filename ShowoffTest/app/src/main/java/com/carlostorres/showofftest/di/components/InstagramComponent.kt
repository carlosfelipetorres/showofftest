package com.carlostorres.showofftest.di.components

import com.carlostorres.showofftest.di.modules.InteractorModule
import com.carlostorres.showofftest.di.modules.RepositoryModule
import com.carlostorres.showofftest.di.modules.ViewModelModule
import com.carlostorres.showofftest.home.HomeFragment
import com.carlostorres.showofftest.login.LoginFragment
import com.carlostorres.showofftest.splash.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, InteractorModule::class, RepositoryModule::class])
interface InstagramComponent {

    fun inject(fragment: SplashFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: HomeFragment)

}