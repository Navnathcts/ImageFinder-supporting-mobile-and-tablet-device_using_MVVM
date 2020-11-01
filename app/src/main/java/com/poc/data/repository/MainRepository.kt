package com.poc.data.repository

import com.poc.data.remote.APIInterface
import com.poc.data.response.ImageListResponse

class MainRepository(private val apiHelper: APIInterface) {
    /**
     * Fetches images based on searchKeyword
     */
    suspend fun getImageByKeyword(searchKeyword: String): ImageListResponse?{
       return apiHelper.getImageByKeyword(searchKeyword)
    }
}