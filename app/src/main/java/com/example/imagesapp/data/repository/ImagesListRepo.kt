package com.example.imagesapp.data.repository

import com.example.imagesapp.data.local.ImagesEntity
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.utils.UiState
import kotlinx.coroutines.flow.Flow

interface ImagesListRepo {

    suspend fun getCharacters(): Flow<UiState<List<CharactersCardItem>>>

    suspend fun insertImagesToDatabaseFromRemote(imagesEntity: List<ImagesEntity>)

    fun getAllImagesFromDB(): Flow<List<ImagesEntity>>

    suspend fun deleteImages(images: List<ImagesEntity>)

    suspend fun deleteAllImages()

}