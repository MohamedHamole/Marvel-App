package com.example.imagesapp.data.repository

import com.example.imagesapp.data.model.UploadImageModel
import com.example.imagesapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface ImageDetailsRepo {

    suspend fun uploadImage(imageUrl: String): Flow<UiState<Response<UploadImageModel>>>

    suspend fun resizeImage(
        imageApi: String, method: String, width: Int, height: Int
    ): Flow<UiState<Response<ResponseBody>>>

}