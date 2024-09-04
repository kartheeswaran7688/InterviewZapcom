package com.karthee.interviewsample.presentation

sealed class Screens(val route:String) {
    data object HOME:Screens("Home")
}