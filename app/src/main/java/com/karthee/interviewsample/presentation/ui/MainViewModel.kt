package com.karthee.interviewsample.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthee.interviewsample.data.ApiResponse
import com.karthee.interviewsample.domain.GetSectionUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val usecase: GetSectionUsecase): ViewModel() {
    val uiFlow = MutableStateFlow(SectionUIModel(UIStatus.Idle))

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSections() {
        viewModelScope.launch {
            usecase().mapLatest {
                when (it) {
                    ApiResponse.LOADING -> SectionUIModel(UIStatus.Loading)
                    is ApiResponse.ERROR -> SectionUIModel(UIStatus.Error(it.msg,it.code))
                    is ApiResponse.SUCCESS -> SectionUIModel(UIStatus.Success, emptyList())
                }
            }.collect{
                uiFlow.value = it
            }

        }
    }

    fun refresh() {
        getSections()
    }
}