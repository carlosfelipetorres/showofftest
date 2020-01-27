package com.carlostorres.showofftest.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Custom provider to inject desired ViewModel injected through Dagger
 */
@Singleton
class ViewModelFactory @Inject constructor(private val creators: MapViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(viewModel: Class<T>): T {
        val creator = creators[viewModel] ?: match(viewModel)
        return try {
            @Suppress("UNCHECKED_CAST")
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun <T : ViewModel> match(viewModel: Class<T>) =
        creators.asIterable().firstOrNull { viewModel.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel: $viewModel")

}