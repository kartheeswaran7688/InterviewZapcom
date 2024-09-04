package com.karthee.interviewsample.domain

import com.karthee.interviewsample.data.ApiResponse
import com.karthee.interviewsample.domain.models.SectionObj
import com.karthee.interviewsample.domain.repo.SectionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSectionUsecase @Inject constructor(val repo: SectionRepo) {
    operator fun invoke():Flow<ApiResponse<List<SectionObj>>> {
        return flow {
            emit(ApiResponse.LOADING)
            emit(repo.fetchAllSections())
        }
    }
}