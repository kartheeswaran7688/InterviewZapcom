package com.karthee.interviewsample.presentation.ui

import com.karthee.interviewsample.domain.models.SectionObj

data class SectionUIModel(val uiStatus: UIStatus, val data: List<SectionObj>? = null)