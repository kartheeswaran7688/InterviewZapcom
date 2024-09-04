package com.karthee.interviewsample.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.karthee.interviewsample.presentation.ui.MainScreen

@Composable
fun NavigationControl(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HOME.route) {
        composable(Screens.HOME.route){
            MainScreen(navController =  navController)
        }
    }
}