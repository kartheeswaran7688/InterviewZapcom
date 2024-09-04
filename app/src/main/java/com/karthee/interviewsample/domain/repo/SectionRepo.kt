package com.karthee.interviewsample.domain.repo

import com.karthee.interviewsample.data.ApiResponse
import com.karthee.interviewsample.domain.models.SectionObj

interface SectionRepo {
    suspend fun fetchAllSections():ApiResponse<List<SectionObj>>
}