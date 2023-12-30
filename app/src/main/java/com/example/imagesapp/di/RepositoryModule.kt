package com.example.imagesapp.di

import com.example.imagesapp.data.repository.ImageDetailsRepo
import com.example.imagesapp.data.repository.ImagesListRepo
import com.example.imagesapp.data.repositoryImpl.ImageDetailsRepoImpl
import com.example.imagesapp.data.repositoryImpl.ImagesListRepoImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<ImagesListRepo>
    { ImagesListRepoImpl(get(), get()) }

    single<ImageDetailsRepo>
    { ImageDetailsRepoImpl(get()) }

}