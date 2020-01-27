package com.carlostorres.showofftest

import androidx.multidex.MultiDexApplication
import com.carlostorres.showofftest.di.components.DaggerInstagramComponent
import com.carlostorres.showofftest.di.components.InstagramComponent
import com.carlostorres.showofftest.di.modules.InteractorModule
import com.carlostorres.showofftest.di.modules.RepositoryModule

class InstagramApplication : MultiDexApplication(){

    fun getInstagramComponent(): InstagramComponent = DaggerInstagramComponent.builder()
        .interactorModule(InteractorModule())
        .repositoryModule(RepositoryModule(applicationContext))
        .build()
}