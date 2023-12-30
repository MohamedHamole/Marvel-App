package com.example.imagesapp.data.remote

import com.example.imagesapp.BuildConfig
import com.example.imagesapp.data.model.ResizeImageRequestModel
import com.example.imagesapp.data.model.UploadImageModel
import com.example.imagesapp.data.model.UploadImageRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface TinyApiService {

    @POST
    @Headers("Authorization: Basic ${BuildConfig.TINY_PNG_API_KEY}", "Content-Type: application/json")
    suspend fun uploadImage(
        @Url url: String,
        @Body request: UploadImageRequest
    ): Response<UploadImageModel>


    @POST
    @Headers("Authorization: Basic ${BuildConfig.TINY_PNG_API_KEY}", "Content-Type: application/json")
    suspend fun resizeImage(
        @Url url: String,
        @Body request: ResizeImageRequestModel
    ): Response<ResponseBody>

}