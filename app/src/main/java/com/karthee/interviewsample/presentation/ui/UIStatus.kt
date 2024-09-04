package com.karthee.interviewsample.presentation.ui

sealed class UIStatus {
    data object Idle   : UIStatus()
    data object Loading: UIStatus()
    data class Error(val msg: String, val code:Int):UIStatus()
    data object Success:UIStatus()
}
