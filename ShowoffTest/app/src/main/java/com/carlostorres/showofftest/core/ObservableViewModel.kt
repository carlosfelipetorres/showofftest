package com.carlostorres.showofftest.core

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.carlostorres.showofftest.BR

/**
 * This abstract class inherit from architecture ViewModel and implements DataBindings observable
 */
abstract class ObservableViewModel: ViewModel(), Observable {

    @delegate:Transient
    private val callbacks: PropertyChangeRegistry by lazy {
        PropertyChangeRegistry()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    fun notifyChange() {
        callbacks.notifyChange(this, BR._all)
    }

}