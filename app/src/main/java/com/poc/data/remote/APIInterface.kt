package com.poc.data.remote

import com.poc.data.response.ImageListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("search/1")
    suspend fun getImageByKeyword(
        @Query(value = "q", encoded = true) searchKeyword: String
    ): ImageListResponse?
}