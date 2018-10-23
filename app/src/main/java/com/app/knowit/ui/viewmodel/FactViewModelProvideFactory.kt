package com.app.knowit.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.app.knowit.data.FactRepository

/**
 * ViewModelProviderFactory helps to create ViewModel with custom constructor
 * [FactViewModelProvideFactory] helps to create [FactViewModel] with [factRepository] as constructor parameter
 */

class FactViewModelProvideFactory(private val factRepository: FactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FactViewModel::class.java)) {
            FactViewModel(factRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}