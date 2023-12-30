package com.example.imagesapp.data.repositoryImpl

import android.util.Log
import com.example.imagesapp.data.makeAPiCall
import com.example.imagesapp.data.model.ResizeImageRequestModel
import com.example.imagesapp.data.model.UploadImageModel
import com.example.imagesapp.data.model.UploadImageRequest
import com.example.imagesapp.data.remote.TinyApiService
import com.example.imagesapp.data.repository.ImageDetailsRepo
import com.example.imagesapp.utils.Constants.RESIZE_URL
import com.example.imagesapp.utils.Constants.UPLOAD_URL
import com.example.imagesapp.utils.UiState
import com.example.imagesapp.utils.extractOutputKey
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

class ImageDetailsRepoImpl(private val tinyApiService: TinyApiService) : ImageDetailsRepo {

    override suspend fun uploadImage(
        imageUrl: String
    ): Flow<UiState<Response<UploadImageModel>>> {
        return makeAPiCall {
            tinyApiService.uploadImage(UPLOAD_URL, UploadImageRequest(UploadImageRequest.Source(imageUrl)))
        }
    }

    override suspend fun resizeImage(
        imageApi: String,
        method: String,
        width: Int,
        height: Int
    ): Flow<UiState<Response<ResponseBody>>> {
        return makeAPiCall {
            val outputKey = imageApi.extractOutputKey()
            Log.i("@@##_imageUrl", imageApi)
            Log.i("@@##_outputKey", outputKey.toString())
            tinyApiService.resizeImage(url = "$RESIZE_URL/output/$outputKey", request =  ResizeImageRequestModel(ResizeImageRequestModel.Resize (method, width, height)))
        }
    }
}