package com.example.imagesapp.navigation

const val ROOT_ROUTE = "root"

sealed class ScreensRoute(val route: String){
    object Splash: ScreensRoute(route = "splash_screen")
    object Images: ScreensRoute(route = "images_screen")
    object Details: ScreensRoute(route = "details_screen")
}