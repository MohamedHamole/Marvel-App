package com.example.imagesapp.data.local.dp

import android.app.Application
import androidx.room.Room
import com.example.imagesapp.data.local.AppDatabase
import com.example.imagesapp.data.local.ImagesDao

object DatabaseBuilder {

    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "APP_DATABASE")
            .fallbackToDestructiveMigration().build()
    }

    fun provideImagesDao(database: AppDatabase): ImagesDao = database.ImagesDao()
}