package com.example.imagesapp.data.remote

import com.example.imagesapp.data.model.CharactersModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoint.IMAGES_LIST)
    suspend fun getImagesList(
        @Header("Requires-Auth") requiresAuth: Boolean = true,
        @Query("limit") limit: Int = 40,
        @Query("offset") offset: Int = 0
    ): CharactersModel

}