package com.example.imagesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Upsert
    suspend fun insertImagesToDatabaseFromRemote(imagesEntity: List<ImagesEntity>)

    @Query("SELECT * FROM images_table")
    fun getAllImagesFromDB(): Flow<List<ImagesEntity>>

    @Query("SELECT * FROM images_table WHERE id = :imageId")
    fun getImageById(imageId: Int): Flow<List<ImagesEntity>>

    @Delete
    suspend fun deleteImages(images: List<ImagesEntity>)

    @Query("DELETE FROM images_table")
    suspend fun deleteAllImages()

}