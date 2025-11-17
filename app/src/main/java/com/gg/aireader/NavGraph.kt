package com.gg.aireader

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gg.aireader.ui.screens.home.HomeScreen


@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController) }
//        composable("reader") { ReaderScreen(navController = navController) }
    }
}