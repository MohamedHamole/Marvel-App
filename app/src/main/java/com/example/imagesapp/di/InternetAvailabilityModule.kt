package com.example.imagesapp.di

import com.example.imagesapp.utils.NetworkConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val internetAvailabilityModule = module {

    single { NetworkConnectivityObserver(androidContext()) }
}