package com.example.imagesapp.customApp

import android.app.Application
import com.example.imagesapp.di.databaseModule
import com.example.imagesapp.di.internetAvailabilityModule
import com.example.imagesapp.di.networkModule
import com.example.imagesapp.di.repositoryModule
import com.example.imagesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CustomApplication)
            modules(networkModule, repositoryModule, viewModelModule, databaseModule, internetAvailabilityModule)
        }
    }
}