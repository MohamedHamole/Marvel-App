package com.example.imagesapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import com.example.imagesapp.data.local.dp.DatabaseBuilder.provideDataBase
import com.example.imagesapp.data.local.dp.DatabaseBuilder.provideImagesDao

val databaseModule = module {
    single { provideDataBase(androidApplication()) }

    single { provideImagesDao(get()) }
}