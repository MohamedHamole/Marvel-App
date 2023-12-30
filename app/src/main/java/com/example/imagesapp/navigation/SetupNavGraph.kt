package com.example.imagesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imagesapp.presentation.images.ImagesListScreen
import com.example.imagesapp.presentation.imageDetails.ImageDetailsScreen
import com.example.imagesapp.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.Splash.route,
        route = ROOT_ROUTE
        ) {
        composable(route = ScreensRoute.Splash.route){
            SplashScreen(navController = navController)
        }
        
        composable(route = ScreensRoute.Images.route){
            ImagesListScreen(navController = navController)
        }

        composable(route = "${ScreensRoute.Details.route}/{imageUrl}/{imageCaption}/{id}") { backStackEntry ->
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            val imageCaption = backStackEntry.arguments?.getString("imageCaption") ?: ""
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ImageDetailsScreen(imageUrl, imageCaption, id, navController = navController, )
        }
    }
}