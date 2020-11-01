package com.poc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poc.data.repository.MainRepository
import com.poc.data.remote.APIInterface

class ViewModelFactory(private val apiHelper: APIInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                MainRepository(
                    apiHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}