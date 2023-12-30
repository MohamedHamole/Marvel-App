package com.example.imagesapp.di

import com.example.imagesapp.presentation.images.ImagesViewModel
import com.example.imagesapp.presentation.imageDetails.ImageDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ImagesViewModel(get()) }

    viewModel { ImageDetailsViewModel(get()) }

}