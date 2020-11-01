package com.poc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.poc.data.response.ImageItem
import com.poc.data.repository.MainRepository
import com.poc.data.response.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private var imageItemsList: MutableList<ImageItem>? = null
    fun getImageByKeyword(searchKeyword: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                withContext(Dispatchers.IO) {
                    imageItemsList = mutableListOf()
                    mainRepository.getImageByKeyword(searchKeyword)?.data?.forEach {
                        if (!it.images.isNullOrEmpty()) {
                            imageItemsList?.add(
                                ImageItem(
                                    id = it.id,
                                    title = it.title,
                                    link = it.images?.get(0)?.link
                                )
                            )

                        }
                    }
                    when {
                        imageItemsList.isNullOrEmpty() -> {
                            emit(
                                Resource.error(
                                    data = null,
                                    message = "We are unable to load results for you...."
                                )
                            )
                        }
                        else -> {
                            emit(
                                Resource.success(
                                    imageItemsList
                                )
                            )
                        }
                    }
                }
            } catch (exception: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        message = "We are unable to load results for you...."
                    )
                )
            }

        }
}