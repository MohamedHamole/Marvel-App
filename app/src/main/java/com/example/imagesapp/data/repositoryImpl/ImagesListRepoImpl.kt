package com.example.imagesapp.data.repositoryImpl

import com.example.imagesapp.data.local.AppDatabase
import com.example.imagesapp.data.local.ImagesEntity
import com.example.imagesapp.data.model.CharactersModel
import com.example.imagesapp.data.makeAPiCall
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.data.remote.ApiService
import com.example.imagesapp.data.repository.ImagesListRepo
import com.example.imagesapp.utils.UiState
import com.example.imagesapp.utils.toCharactersCardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ImagesListRepoImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : ImagesListRepo {

        override suspend fun getCharacters(): Flow<UiState<List<CharactersCardItem>>> {
        return makeAPiCall {
            val charactersModel: CharactersModel = apiService.getImagesList()
            return@makeAPiCall charactersModel.toCharactersCardItem()
        }
    }

    override suspend fun insertImagesToDatabaseFromRemote(imagesEntity: List<ImagesEntity>) {
        return appDatabase.ImagesDao().insertImagesToDatabaseFromRemote(imagesEntity)
    }

    override fun getAllImagesFromDB(): Flow<List<ImagesEntity>> {
        return appDatabase.ImagesDao().getAllImagesFromDB()
    }

    override suspend fun deleteImages(images: List<ImagesEntity>) {
        return appDatabase.ImagesDao().deleteImages(images)
    }

    override suspend fun deleteAllImages() {
        return appDatabase.ImagesDao().deleteAllImages()
    }

}