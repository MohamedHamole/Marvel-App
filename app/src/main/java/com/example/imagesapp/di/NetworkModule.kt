package com.example.imagesapp.di

import com.example.imagesapp.data.remote.ApiNetwork.provideApiService
import com.example.imagesapp.data.remote.ApiNetwork.provideOkHttpClient
import com.example.imagesapp.data.remote.ApiNetwork.provideRetrofit
import com.example.imagesapp.data.remote.ApiNetwork.provideTinyApiService
import com.example.imagesapp.utils.Constants.BASE_URL
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BASE_URL) }
    single { provideApiService(get()) }
    single { provideTinyApiService(get()) }
}